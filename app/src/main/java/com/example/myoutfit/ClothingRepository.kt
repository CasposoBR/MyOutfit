package com.example.myoutfit

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClothingRepository @Inject constructor(private val dao: ClothingItemDao) {
    fun getAllItems(): Flow<List<ClothingItem>> = dao.getAllItems()

    fun getRoupasPorCategoria(category: TagTypeClothes): Flow<List<ClothingItem>> =
        dao.getItemsByCategory(category)

    suspend fun insertInitialData() {
        dao.insertAll(ClothingInventory.items)
    }
}