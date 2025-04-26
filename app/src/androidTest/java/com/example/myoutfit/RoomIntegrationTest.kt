package com.example.myoutfit

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myoutfit.Database.AppDatabase
import com.example.myoutfit.Database.ClothingItemDao
import com.example.myoutfit.Database.ClothingItem
import com.example.myoutfit.Database.StyleTag
import com.example.myoutfit.Database.TagTypeClothes
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomIntegrationTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: ClothingItemDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build() // Removido allowMainThreadQueries()
        dao = db.clothingItemDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetItems() = runBlocking {
        val item = ClothingItem(
            name = "Jaqueta Preta PU",
            price = "199.99",
            imageUrl = "url",
            purchaseLink = "link",
            category = TagTypeClothes.JACKETS,
            tags = listOf(StyleTag.CASUAL, StyleTag.WINTER, StyleTag.ALL),
            isFavorite = false
        )

        dao.insert(item)

        val result = dao.getAllItemsOnce()
        assertEquals(1, result.size)
        assertEquals("Jaqueta Preta PU", result[0].name) // Corrigido o nome
    }
}