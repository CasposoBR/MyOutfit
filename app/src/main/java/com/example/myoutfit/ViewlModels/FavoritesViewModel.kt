package com.example.myoutfit.ViewlModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myoutfit.Database.ClothingItem
import com.example.myoutfit.Database.ClothingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: ClothingRepository
) : ViewModel() {

    val favoriteItems = repository.getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleFavorite(item: ClothingItem) {
        viewModelScope.launch {
            repository.toggleFavorite(item)
        }
    }
    // Remover item dos favoritos
    fun removeFavorite(item: ClothingItem) {
        viewModelScope.launch {
            repository.removeFavorite(item)
        }
    }


}