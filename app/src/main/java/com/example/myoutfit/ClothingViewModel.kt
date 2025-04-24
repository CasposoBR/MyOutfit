package com.example.myoutfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClothingViewModel @Inject constructor(
    private val repository: ClothingRepository
) : ViewModel() {

    val allItems: StateFlow<List<ClothingItem>> = repository.getAllItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.insertInitialDataIfNeeded(ClothingInventory.items)
        }
    }

    fun getProductsByQuery(query: String): StateFlow<List<ClothingItem>> {
        return allItems.map { items ->
            if (query.isBlank()) emptyList()
            else items.filter { it.name.contains(query, ignoreCase = true) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
    fun toggleFavorite(item: ClothingItem) {
        val updated = item.copy(isFavorite = !item.isFavorite)
        // Atualize o repositório ou a lista (dependendo da arquitetura)
    }

}