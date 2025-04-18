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

    fun loadProductsByCategory(categoryType: CategoryType) {
        _isLoading.value = true
        _error.value = null
        // Simulação de carregamento de dados (substitua com chamada a API ou banco de dados)
        try {
            // Supondo que getProducts() retorne uma lista de `Product`, você pode fazer o cast ou conversão para `ClothingItem`
            val filteredProducts = getProducts().filter { it.categoryType == categoryType }
            // Se getProducts() retorna Product, você pode mapear para ClothingItem
            _products.value = filteredProducts.map { product ->
                ClothingItem(
                    name = product.title,
                    price = product.price,
                    imageUrl = product.imageUrl,
                    purchaseLink = product.link,
                    category = product.categoryType,
                    // Aqui você precisa mapear as tags corretamente, assumindo que você tenha uma lógica para isso
                    tags = mapTags(product.tags)
                )
            }
        } catch (e: Exception) {
            _error.value = e.message
        } finally {
            _isLoading.value = false
        }
    }

    // Função para mapear as tags de String para List<StyleTag> conforme necessário
    fun mapTags(tags: String): List<StyleTag> {
        return tags.split(",").mapNotNull { tag ->
            try {
                StyleTag.valueOf(
                    tag.trim().uppercase()
                )  // Supondo que as tags no String sejam separadas por vírgula
            } catch (e: IllegalArgumentException) {
                null  // Retorna null se o valor não for um StyleTag válido
            }
        }
    }
}