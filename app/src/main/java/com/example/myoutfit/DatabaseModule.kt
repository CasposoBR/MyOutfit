package com.example.myoutfit

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "myoutfit-db"
        ).build()
    }

    @Provides
    fun provideClothingItemDao(database: AppDatabase): ClothingItemDao {
        return database.clothingItemDao()
    }

    @Provides
    @Singleton
    fun provideClothingRepository(dao: ClothingItemDao): ClothingRepository {
        return ClothingRepository(dao)
    }

}