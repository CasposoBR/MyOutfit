package com.example.myoutfit

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClothingRepository @Inject constructor(private val dao: ClothingItemDao) {
    fun getRoupasPorCategoria(category: String): Flow<List<ClothingItem>> =
        dao.getItemsByCategory(category)



    fun getAllItems(): Flow<List<ClothingItem>> = dao.getAllItems()

    suspend fun insertInitialData(items: List<ClothingItem>) {
        dao.insertAll(items)
    }

}
