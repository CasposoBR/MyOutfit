package com.example.myoutfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ClothingItem>)

    @Query("SELECT * FROM clothing_items")
    fun getAllItems(): Flow<List<ClothingItem>>

    @Query("SELECT * FROM clothing_items WHERE category = :category")
    fun getItemsByCategory(category: TagTypeClothes): Flow<List<ClothingItem>>



}