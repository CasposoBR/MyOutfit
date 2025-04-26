package com.example.myoutfit


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ClothingViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
    fun toggleFavorite_shouldFlipIsFavorite() = runTest(testDispatcher) {
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
        advanceUntilIdle() // Espera as corrotinas terminarem

        val updatedItem = fakeRepository.daoAccess.lastUpdatedItem
        Assert.assertEquals(item.copy(isFavorite = true), updatedItem)
    }
}


