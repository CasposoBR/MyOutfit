package com.example.myoutfit

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [ClothingItem::class],
    version = 2,  // Incrementando a versão para 2
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clothingItemDao(): ClothingItemDao
}

// Definindo a migração para a versão 2
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Exemplo: Adicionando uma nova coluna chamada 'new_column' à tabela 'ClothingItem'
        db.execSQL("ALTER TABLE ClothingItem ADD COLUMN new_column INTEGER DEFAULT 0 NOT NULL")
    }
}