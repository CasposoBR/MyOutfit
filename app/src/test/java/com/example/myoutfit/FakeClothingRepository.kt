package com.example.myoutfit

import com.example.myoutfit.Database.ClothingRepository

class FakeClothingRepository(
    val daoAccess: FakeClothingItemDao = FakeClothingItemDao()
) : ClothingRepository(daoAccess)