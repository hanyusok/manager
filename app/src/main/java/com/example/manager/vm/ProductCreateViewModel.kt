package com.example.manager.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manager.model.Product
import com.example.manager.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProductCreateViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel()
{
    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    private val _showSuccessMessage = MutableStateFlow(false)
    val showSuccessMessage: Flow<Boolean> = _showSuccessMessage

    fun onCreateProduct(name: String, price: Double) {
        if (name.isEmpty() || price <= 0) return
        viewModelScope.launch {
            _isLoading.value = true
            val product = Product(
                id = UUID.randomUUID().toString(),
                name = name,
                price = price,
            )
            productRepository.createProduct(product = product)
            _isLoading.value = false
            _showSuccessMessage.emit(true)

        }
    }
}

interface ProductCreateContract {
    val navigateProductCreateSuccess: Flow<ProductCreateUseCase.Output?>
    val isLoading: Flow<Boolean>
    val showSuccessMessage: Flow<Boolean>
    fun onProductCreate(name: String, price: Double)
    fun onAddMoreProductSelected()
    fun onRetrySelected()
}

interface ProductCreateUseCase : UseCase<ProductCreateContract, ProductCreateContract> {
    class Input(val product: Product)
    sealed class Output {
        class Success(val result: Boolean) : Output()
        open class Failure : Output() {
            object Conflict : Failure()
            object Unauthorized : Failure()
            object BadRequest : Failure()
            object InternalError : Failure()
        }
    }
}

interface UseCase<InputT, OutputT> {
    suspend fun execute(input: InputT): OutputT
}