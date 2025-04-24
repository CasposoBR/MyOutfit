package com.example.myoutfit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class CategoryViewModel @Inject constructor(
    private val repository: ClothingRepository
) : ViewModel() {

    private var alreadyInserted = false

    private val _products = MutableStateFlow<List<ClothingItem>>(emptyList())
    val products: StateFlow<List<ClothingItem>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadProductsByCategory(category: TagTypeClothes) {
        Log.d("CategoryDebug", "Carregando produtos da categoria: $category")
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getRoupasPorCategoria(category).collect { roupas ->
                    _products.value = roupas
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = "Erro ao carregar produtos."
                _isLoading.value = false
            }
        }
    }
}


