package com.example.myoutfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClothingViewModel(
    private val repository: ClothingRepository
) : ViewModel() {

    val allItems: StateFlow<List<ClothingItem>> = repository.getAllItems().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insertInitialData() {
        viewModelScope.launch {
            // Passar a lista de itens para a função insertInitialData
            repository.insertInitialData(ClothingInventory.items) // Ou qualquer lista que você tenha
        }
    }

    fun getByCategory(category: TagTypeClothes): Flow<List<ClothingItem>> {
        return repository.getRoupasPorCategoria(category.name)
    }
}