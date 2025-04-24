package com.example.myoutfit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class ClothingRepositoryTest {

    private lateinit var repository: ClothingRepository
    private lateinit var fakeDao: FakeClothingItemDao

    @Before
    fun setup() {
        fakeDao = FakeClothingItemDao()
        repository = ClothingRepository(fakeDao)
    }

    @Test
    fun toggleFavorite_shouldFlipIsFavorite() = runBlocking {
        val item = ClothingItem(
            id = 1,
            name = "Tênis",
            price = "199.99",
            imageUrl = "url",
            purchaseLink = "link",
            category = TagTypeClothes.SHOES,
            tags = listOf(StyleTag.URBAN),
            isFavorite = false
        )
        fakeDao.insertAll(listOf(item))

        repository.toggleFavorite(item)

        val updated = fakeDao.updatedItem
        assertNotNull(updated)
        assertTrue(updated!!.isFavorite)
    }

    class FakeClothingItemDao : ClothingItemDao {
        var updatedItem: ClothingItem? = null
        private val items = mutableListOf<ClothingItem>()

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
            updatedItem = item
        }

        override suspend fun getAllItemsOnce(): List<ClothingItem> = items
    }




}