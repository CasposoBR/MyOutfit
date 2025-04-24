package com.example.myoutfit

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class ClothingRepository @Inject constructor(private val dao: ClothingItemDao) {

    fun getRoupasPorCategoria(category: TagTypeClothes): Flow<List<ClothingItem>> =
        dao.getItemsByCategory(category)

    fun getAllItems(): Flow<List<ClothingItem>> = dao.getAllItems()

    suspend fun insertInitialData(items: List<ClothingItem>) {
        dao.insertAll(items)
    }
    suspend fun insertInitialDataIfNeeded(items: List<ClothingItem>) {
        val currentItems = dao.getAllItemsOnce() // cria uma função DAO que retorna List<ClothingItem>
        if (currentItems.isEmpty()) {
            dao.insertAll(items)
        }
    }
    fun removeFavorite(item: ClothingItem) {
        // lógica para remover o item dos favoritos no banco de dados ou repositório
    }

    fun getFavorites(): Flow<List<ClothingItem>> = dao.getFavorites()

    suspend fun toggleFavorite(item: ClothingItem) {
        val updatedItem = item.copy(isFavorite = !item.isFavorite)
        dao.updateItem(updatedItem)
    }
    suspend fun insertItems(items: List<ClothingItem>) {
        dao.insertAll(items)
    }

}
