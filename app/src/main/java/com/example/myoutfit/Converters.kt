package com.example.myoutfit

import androidx.room.TypeConverter

class Converters {


    @TypeConverter
    fun fromTagTypeClothes(value: TagTypeClothes): String = value.name

    @TypeConverter
    fun toTagTypeClothes(value: String): TagTypeClothes = TagTypeClothes.valueOf(value)

    @TypeConverter
    fun fromStyleTagList(tags: List<StyleTag>): String =
        tags.joinToString(",") { it.name }

    @TypeConverter
    fun toStyleTagList(data: String): List<StyleTag> =
        if (data.isBlank()) emptyList() else data.split(",").map { StyleTag.valueOf(it) }



}