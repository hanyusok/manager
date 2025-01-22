package com.example.manager.repo

import com.example.manager.BuildConfig
import com.example.manager.model.Product
import com.example.manager.model.ProductDto
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val storage: Storage
) : ProductRepository {
    override suspend fun createProduct(product: Product): Boolean{
        return try {
            withContext(Dispatchers.IO){
                val productDto = ProductDto(
                    name = product.name,
                    price = product.price,
                    image = product.image
                )
                postgrest["products"].insert(productDto)
                true
            }
            true
        } catch (e: Exception){
            throw e
        }
    }
    override suspend fun getProducts(): List<ProductDto>?{
        return try {
            withContext(Dispatchers.IO){
                val response = postgrest["products"].select().decodeList<ProductDto>()
                return@withContext response
            }
        } catch (e: Exception){
            throw e
        }
    }
    override suspend fun getProduct(id: String): ProductDto{
        return try {
            withContext(Dispatchers.IO){
                val response = postgrest["products"].select { filter { eq("id", id) } }.decodeSingle<ProductDto>()
                return@withContext response
            }
        } catch (e: Exception){
            throw e
        }
    }
    override suspend fun deleteProduct(id: String){
        return withContext(Dispatchers.IO){
            postgrest["products"].delete {
                filter {
                    eq("id", id)
                }
            }
        }
    }
    override suspend fun updateProduct(
        id: String,
        name: String,
        price: Double,
        imageName: String,
        imageFile: ByteArray
    ){
        withContext(Dispatchers.IO) {
            if (imageFile.isNotEmpty()) {
                val imageUrl =
                    storage["Product%20Image"].upload(
                        path = "$imageName.png",
                        data = imageFile
                    ){
                        upsert = false
                    }
                postgrest.from("products").update({
                    set("name", name)
                    set("price", price)
                    set("image", buildImageUrl(imageFileName = imageUrl as String))
                }) {
                    filter {
                        eq("id", id)
                    }
                }
            } else {
                postgrest.from("products").update({
                    set("name", name)
                    set("price", price)
                }) {
                    filter {
                        eq("id", id)
                    }
                }
            }
        }
    }

    private fun buildImageUrl(imageFileName: String) : String {
        return "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/${imageFileName}".replace(" ", "%20")
    }

}