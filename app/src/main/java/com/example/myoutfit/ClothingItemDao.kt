package com.example.myoutfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingItemDao {

    @Query("SELECT * FROM clothing_item WHERE category = :category")
    fun getItemsByCategory(category: String): Flow<List<ClothingItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ClothingItem>)

    @Query("SELECT * FROM clothing_item")
    suspend fun getAllItemsOnce(): List<ClothingItem>

    @Query("SELECT * FROM clothing_item")
    fun getAllItems(): Flow<List<ClothingItem>>
}