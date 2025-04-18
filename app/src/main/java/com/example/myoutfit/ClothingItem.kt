package com.example.myoutfit


enum class StyleTag {
    TREND, SUMMER, WINTER, WORKOUT, CASUAL, FORMAL, URBAN
}

data class ClothingItem(
    val name: String,
    val price: String,
    val imageUrl: String,
    val purchaseLink: String,
    val category: CategoryType,
    val tags: List<StyleTag> = emptyList()
)

object ClothingInventory {
    val items = listOf(
        ClothingItem(
            name = "Short de linho bege",
            price = "R$ 70,00",
            imageUrl = "https://i.imgur.com/Ai3TAr2.jpg",
            purchaseLink = "https://br.shein.com/Shorts-Masculino-Casual-S%C3%B3lido-C%C3%B3s-M%C3%A9dio-com-Cord%C3%A3o-p-19337025-cat-1972.html",
            category = CategoryType.SHORTS,
            tags = listOf(StyleTag.SUMMER, StyleTag.TREND)
        ),
        ClothingItem(
            name = "Camisa Social Preta",
            price = "R$ 89,99",
            imageUrl = "https://i.imgur.com/5T3aN4v.jpg",
            purchaseLink = "https://br.shein.com/Camisa-Social-Preta-Masculina-p-12345678.html",
            category = CategoryType.FORMAL,
            tags = listOf(StyleTag.FORMAL)
        ),
        ClothingItem(
            name = "Tênis Branco Minimalista",
            price = "R$ 150,00",
            imageUrl = "https://i.imgur.com/KmWAlWi.jpg",
            purchaseLink = "https://br.shein.com/Tenis-Branco-Minimalista-Masculino-p-87654321.html",
            category = CategoryType.SHOES,
            tags = listOf(StyleTag.URBAN, StyleTag.CASUAL)
        )
        // Você pode adicionar quantos quiser aqui
    )
}