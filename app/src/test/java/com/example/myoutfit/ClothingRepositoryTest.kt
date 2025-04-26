package com.example.myoutfit

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

        val updated = fakeDao.lastUpdatedItem
        assertNotNull(updated)
        assertTrue(updated!!.isFavorite)
    }
}



