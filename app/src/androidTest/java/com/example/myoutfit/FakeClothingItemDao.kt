package com.example.myoutfit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeClothingItemDao : ClothingItemDao {
    private val items = mutableListOf<ClothingItem>()
    var lastUpdatedItem: ClothingItem? = null

    override fun getAllItems(): Flow<List<ClothingItem>> = flowOf(items)

    override fun getItemsByCategory(category: TagTypeClothes): Flow<List<ClothingItem>> =
        flowOf(items.filter { it.category == category })

    override fun getFavorites(): Flow<List<ClothingItem>> = flowOf(items.filter { it.isFavorite })

    override fun getAllFavorites(): Flow<List<ClothingItem>> = getFavorites()

    override suspend fun insertAll(items: List<ClothingItem>) {
        this.items.clear()
        this.items.addAll(items)
    }

    override suspend fun updateItem(item: ClothingItem) {
        lastUpdatedItem = item
        val index = items.indexOfFirst { it.id == item.id }
        if (index != -1) items[index] = item
    }

    override suspend fun getAllItemsOnce(): List<ClothingItem> = items
}