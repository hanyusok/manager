package com.example.manager.vm

import com.example.manager.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductListContract {
    val productList: Flow<List<Product>?>
    fun removeItem(product: Product)
    fun getProducts()
}