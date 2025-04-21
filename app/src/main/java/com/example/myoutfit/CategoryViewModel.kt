package com.example.myoutfit

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

    private val _products = MutableStateFlow<List<ClothingItem>>(emptyList())
    val products: StateFlow<List<ClothingItem>> = _products

    fun loadProductsByCategory(categoryType: TagTypeClothes) {
        viewModelScope.launch {
            repository.getRoupasPorCategoria(categoryType) // Passe o TagTypeClothes diretamente
                .collect { roupas ->
                    _products.value = roupas
                }
        }
    }
}


