package com.example.manager.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manager.model.Product
import com.example.manager.model.ProductDto
import com.example.manager.nav.ProductListDestination
import com.example.manager.repo.ProductRepository
import com.example.manager.repo.ProductRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    private val _productList = MutableStateFlow<List<Product>?>(listOf())
    val productList: Flow<List<Product>?> = _productList

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            val products = productRepository.getProducts()
            _productList.emit(products?.map { it -> it.asDomainModel() })
        }
    }

    fun removeItem(product: Product) {
        viewModelScope.launch {
            val newList = mutableListOf<Product>().apply { _productList.value?.let { addAll(it) } }
            newList.remove(product)
            _productList.emit(newList.toList())
            // Call api to remove
            productRepository.deleteProduct(id = product.id)
            // Then fetch again
            getProducts()
        }
    }

    private fun ProductDto.asDomainModel(): Product {
        return Product(
            id = this.id.toString(),
            name = this.name,
            price = this.price,
            image = this.image.toString()
        )
    }

}