package com.example.manager.repo

import com.example.manager.model.Product
import com.example.manager.model.ProductDto

interface ProductRepository {
    suspend fun createProduct(product: Product): Boolean
    suspend fun getProducts(): List<ProductDto>?
    suspend fun getProduct(id: String): ProductDto
    suspend fun deleteProduct(id: String)
    suspend fun updateProduct(
        id: String, name: String, price: Double, imageName: String, imageFile: ByteArray
    )
}