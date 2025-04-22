package com.example.myoutfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClothingViewModel(
    private val repository: ClothingRepository
) : ViewModel() {

    // Estado com todos os itens de roupas
    val allItems: StateFlow<List<ClothingItem>> = repository.getAllItems().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Função para inserir dados iniciais no banco
    fun insertInitialData() {
        viewModelScope.launch {
            repository.insertInitialData(ClothingInventory.items)  // Insira os itens iniciais
        }
    }

    // Função para filtrar os itens de acordo com a categoria
    fun getProductsByCategory(category: TagTypeClothes): StateFlow<List<ClothingItem>> {
        return repository.getAllItems()
            .map { allItems ->
                allItems.filter { it.category == category }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }
}