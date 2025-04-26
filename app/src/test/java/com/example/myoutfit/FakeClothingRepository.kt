package com.example.myoutfit

class FakeClothingRepository(
    val daoAccess: FakeClothingItemDao = FakeClothingItemDao()
) : ClothingRepository(daoAccess)