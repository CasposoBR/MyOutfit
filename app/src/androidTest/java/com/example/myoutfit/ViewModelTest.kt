package com.example.myoutfit

import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ClothingViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ClothingViewModel
    private lateinit var fakeRepository: FakeClothingRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeClothingRepository()
        viewModel = ClothingViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun toggleFavorite_shouldFlipIsFavorite(): Unit = runBlocking {
        run {
            val item = ClothingItem(
                id = 1,
                name = "Camisa",
                price = "59.90",
                imageUrl = "url",
                purchaseLink = "link",
                category = TagTypeClothes.SHIRTS,
                tags = listOf(StyleTag.CASUAL),
                isFavorite = false
            )
            fakeRepository.insertAll(listOf(item))

            viewModel.toggleFavorite(item)
            advanceUntilIdle() // garante que coroutine termine

            val updatedItem = fakeRepository.updatedItem
            assertNotNull(updatedItem)
            assertTrue(updatedItem!!.isFavorite)
        }
}

class FakeClothingRepository : ClothingRepository(
    object : ClothingItemDao {
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
            val index = items.indexOfFirst { it.id == item.id }
            if (index != -1) items[index] = item
        }

        override suspend fun getAllItemsOnce(): List<ClothingItem> = items
    })
    }

