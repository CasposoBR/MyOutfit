package com.example.myoutfit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {
    private val _products = mutableStateOf<List<ClothingItem>>(emptyList())
    val products: State<List<ClothingItem>> get() = _products

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> get() = _error

    fun loadProductsByCategory(categoryType: TagTypeClothes) {
        _isLoading.value = true
        _error.value = null

        try {
            val filteredProducts = getProducts().filter { it.category == categoryType }
            _products.value = filteredProducts.map { product ->
                ClothingItem(
                    name = product.name,
                    price = product.price,
                    imageUrl = product.imageUrl,
                    purchaseLink = product.purchaseLink,
                    category = product.category,
                    tags = product.tags
                )
            }
        } catch (e: Exception) {
            _error.value = "Erro ao carregar produtos"
        } finally {
            _isLoading.value = false
        }
    }

}

