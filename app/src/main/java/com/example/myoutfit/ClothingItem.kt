package com.example.myoutfit

data class ClothingItem(
    val name: String,
    val price: String,
    val imageUrl: String,
    val purchaseLink: String,
    val category: String

)

object ClothingInventory {
    val items = listOf(
        ClothingItem(
            name = "Short de linho bege",
            price = "R$ 70,00",
            imageUrl = "https://i.imgur.com/Ai3TAr2.jpg",
            purchaseLink = "https://br.shein.com/Shorts-Masculino-Casual-S%C3%B3lido-C%C3%B3s-M%C3%A9dio-com-Cord%C3%A3o-p-19337025-cat-1972.html",
            category = "Shorts"
        ),
        ClothingItem(
            name = "Camisa Social Preta",
            price = "R$ 89,99",
            imageUrl = "https://link.da.imagem.com",
            purchaseLink = "https://link.para.compra.com",
            category = "Camisas"
        )
        // Adicione quantos quiser
    )
}