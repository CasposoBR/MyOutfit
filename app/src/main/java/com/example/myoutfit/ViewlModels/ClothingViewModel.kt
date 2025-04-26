package com.example.myoutfit.ViewlModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myoutfit.Database.ClothingInventory
import com.example.myoutfit.Database.ClothingItem
import com.example.myoutfit.Database.ClothingRepository
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
        viewModelScope.launch {
            val updated = item.copy(isFavorite = !item.isFavorite)
            repository.updateItem(updated)
        }
    }
    }