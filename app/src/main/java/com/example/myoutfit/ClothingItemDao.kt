package com.example.myoutfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingItemDao {

    @Query("SELECT * FROM clothing_item")
    fun getAllItems(): Flow<List<ClothingItem>>

    @Query("SELECT * FROM clothing_item WHERE category = :category")
    fun getItemsByCategory(category: TagTypeClothes): Flow<List<ClothingItem>>

    @Query("SELECT * FROM clothing_item WHERE isFavorite = 1")
    fun getAllFavorites(): Flow<List<ClothingItem>>

    @Query("SELECT * FROM clothing_item WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<ClothingItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ClothingItem>)

    @Update
    suspend fun updateItem(item: ClothingItem)

    @Query("SELECT * FROM clothing_item")
    suspend fun getAllItemsOnce(): List<ClothingItem>
}