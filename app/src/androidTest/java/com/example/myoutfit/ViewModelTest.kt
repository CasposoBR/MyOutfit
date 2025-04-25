package com.example.myoutfit


import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class ClothingViewModelTest {

    private lateinit var viewModel: ClothingViewModel
    private lateinit var fakeRepository: FakeClothingRepository

    @Before
    fun setup() {
        fakeRepository = FakeClothingRepository()
        viewModel = ClothingViewModel(fakeRepository)
    }

    @Test
    fun toggleFavorite_shouldFlipIsFavorite() = runBlocking {
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
        fakeRepository.daoAccess.insertAll(listOf(item))

        viewModel.toggleFavorite(item)

        val updatedItem = fakeRepository.daoAccess.lastUpdatedItem
        assertNotNull(updatedItem)
        assertTrue(updatedItem!!.isFavorite)
    }
}

class FakeClothingRepository(
    val daoAccess: FakeClothingItemDao = FakeClothingItemDao()
) : ClothingRepository(daoAccess)


