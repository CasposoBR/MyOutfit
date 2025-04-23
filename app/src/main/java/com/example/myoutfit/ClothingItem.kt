    package com.example.myoutfit

    import androidx.room.Entity
    import androidx.room.PrimaryKey


    enum class StyleTag {
        TREND, SUMMER, WINTER, WORKOUT, CASUAL, FORMAL, URBAN, OLDMONEY, CLASSIC, ALL
    }
    enum class TagTypeClothes(val displayName: String) {
        T_SHIRTS("Camisetas"),
        SHIRTS("Camisas"),
        PANTS("Calças"),
        SHORTS("Shorts"),
        JACKETS("Jaquetas"),
        SHOES("Tênis"),
        ACCESSORIES("Acessórios"),
        ALL("Todos")
    }
    @Entity(tableName = "clothing_item")
    data class ClothingItem(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val name: String,
        val price: String,
        val imageUrl: String,
        val purchaseLink: String,
        val category: TagTypeClothes,
        val tags: List<StyleTag>
    )

    object ClothingInventory {
        val items = listOf(
            ClothingItem(
                name = "Short de linho bege",
                price = "R$ 70,00",
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2023/06/14/16867490175d8a85151e143a2e0dd205ae7c49eacd.webp",
                purchaseLink = "https://br.shein.com/Men-Shorts-p-17966745.html",
                    category = TagTypeClothes.SHORTS,
                tags = listOf(StyleTag.SUMMER, StyleTag.ALL)
            ),
            ClothingItem(
                name = "Camisa Social Preta",
                price = "R$ 89,99",
                imageUrl = "https://img.ltwebstatic.com/images3_pi/2024/11/30/67/17329459297f7a89b48cdcd29be54bbd8cffa7a507.webp",
                purchaseLink = "https://br.shein.com/Manfinity-Homme-Men-s-Autumn-Solid-Color-Single-Breasted-Casual-Long-Sleeve-Shirt-p-49174856.html",
                category = TagTypeClothes.SHIRTS,
                tags = listOf(StyleTag.FORMAL, StyleTag.OLDMONEY, StyleTag.ALL )
            ),
            ClothingItem(
                name = "Tênis Branco Minimalista",
                price = "R$ 150,00",
                imageUrl = "https://i.imgur.com/KmWAlWi.jpg",
                purchaseLink = "https://br.shein.com/Tenis-Branco-Minimalista-Masculino-p-87654321.html",
                category = TagTypeClothes.SHOES,
                tags = listOf(StyleTag.URBAN, StyleTag.CASUAL, StyleTag.ALL)
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_pi/2024/08/28/b7/1724811269f1b9f1b91b4f0b2dc34b7f2aca60c8a5.webp",
                name = "Jaqueta Preta PU",
                purchaseLink = "https://br.shein.com/SWAVVY-Men-s-Fashionable-Daily-Casual-PU-Leather-Fitted-Long-Sleeve-Men-Jacket-Urban-Black-Jacket-For-Friends-Husband-Boyfriend-Gifts-p-41641968.html?mallCode=1&imgRatio=3-4",
                category = TagTypeClothes.JACKETS,
                tags = listOf(StyleTag.CASUAL, StyleTag.WINTER, StyleTag.URBAN, StyleTag.ALL),
                price = "R$ 199,99"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/10/03/d3/172796272339daa9f54e33fe4bff473d187a24f118_thumbnail_336x.webp",
                name = "Camiseta Oversized",
                purchaseLink = "https://br.shein.com/Men-T-Shirts-p-25143085.html",
                category = TagTypeClothes.T_SHIRTS,
                tags = listOf(StyleTag.TREND, StyleTag.CASUAL, StyleTag.ALL),
                price = "R$ 79,90"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2023/06/12/16865104766b5b3b261d29d57196290c691452416f.webp",
                name = "Jaqueta Jeans",
                purchaseLink = "https://br.shein.com/Men-Denim-Jackets-p-14722960.html",
                category = TagTypeClothes.JACKETS,
                tags = listOf(StyleTag.WINTER, StyleTag.CASUAL, StyleTag.ALL),
                price = "R$ 114,97"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_pi/2024/03/06/59/17097300463527cb3179a7e75552444fd907ba6b23.webp",
                name = "Calça Alfaiataria",
                purchaseLink = "https://br.shein.com/Manfinity-Homme-Men-s-Casual-Solid-Color-Slant-Pocket-Drawstring-Pants-Straight-Leg-Long-Slacks-Plain-Going-Out-p-3115655.html",
                category = TagTypeClothes.PANTS,
                tags = listOf(StyleTag.TREND, StyleTag.FORMAL, StyleTag.ALL),
                price = "R$ 62,18"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2023/09/02/9a/1693662275a6500efb50040557607c45a67aa3b08c.webp",
                name = "Regata Canelada",
                purchaseLink = "https://br.shein.com/Men-Tank-Tops-p-22602261.html",
                category = TagTypeClothes.T_SHIRTS,
                tags = listOf(StyleTag.SUMMER, StyleTag.CASUAL, StyleTag.ALL),
                price = "R$ 24,21"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2025/02/19/4c/17399750382ff6a6719daecabbd80697b67f8fc201_square.webp",
                name = "Camisa Dry Fit",
                purchaseLink = "https://br.shein.com/Men-s-Short-Sleeve-Dry-Fit-Gym-Fitness-T-Shirt-Lisa-Plus-Size-Zoo-p-48405365.html",
                category = TagTypeClothes.T_SHIRTS,
                tags = listOf(StyleTag.WORKOUT, StyleTag.CASUAL, StyleTag.ALL),
                price = "A partir de R$22,49"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/08/17/97/1723836643d25ffdb991394f79b4bce2ac1d8f3419_thumbnail_336x.webp",
                name = "Camisa Oversized Academia Fit",
                purchaseLink = "https://br.shein.com/Oversized-Shirt-Gym-Fashion-Fit-Bodybuilding-p-40991466.html",
                category = TagTypeClothes.T_SHIRTS,
                tags = listOf(StyleTag.WORKOUT, StyleTag.TREND, StyleTag.ALL),
                price = "R$39,95"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/gspCenter/goodsImage/2023/1/31/4832058447_1029674/00ABECC87B4CB31F239FC5CE93594C71.webp",
                name = "Slim Fitness SLM Camiseta UV Masculina Proteção Solar",
                purchaseLink = "https://br.shein.com/Slim-Fitness-SLM-Men-T-Shirts-Tanks-p-12893370.html",
                category = TagTypeClothes.SHIRTS,
                tags = listOf(StyleTag.WORKOUT, StyleTag.SUMMER, StyleTag.ALL),
                price = "R$ 23,64"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/12/19/80/17346196374ea253869f05097f48bd52ce0311635f.webp",
                name = "Camisa de Linho Manga curta Social",
                purchaseLink = "https://br.shein.com/Linen-Shirt-Short-Sleeve-Formal-Summer-Fresh-Italian-Collar-Elegant-In-Visco-Linen-p-50917714.html",
                category = TagTypeClothes.SHIRTS,
                tags = listOf(StyleTag.SUMMER, StyleTag.FORMAL, StyleTag.ALL),
                price = "R$ 54,90"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/01/30/1b/1706556936ae19911a343e9b7ba6a9732a033a3107.webp",
                name = "Calça Masculina Cargo Sarja Skatista",
                purchaseLink = "https://br.shein.com/Men-Jeans-p-30021128.html",
                category = TagTypeClothes.PANTS,
                tags = listOf(StyleTag.TREND, StyleTag.URBAN, StyleTag.ALL),
                price = "R$ 78,99"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/02/02/28/17068460474610b8f45f7cdfb741129b0b4b5abaa4.webp",
                name = "Bermuda Tecido Sarja",
                purchaseLink = "https://br.shein.com/Men-Shorts-p-13972715.html",
                category = TagTypeClothes.SHORTS,
                tags = listOf(StyleTag.SUMMER, StyleTag.CASUAL, StyleTag.ALL),
                price = "R$ 55,90"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2025/03/17/98/1742169872c605f20beaacf7759ac2b836b3552f2e.webp",
                name = "Calça Jeans Preta Bag Balão",
                purchaseLink = "https://br.shein.com/Bag-Balloon-Jeans-Wide-Straight-Cut-Carpenter-UNISEX-p-61215286.html",
                category = TagTypeClothes.PANTS,
                tags = listOf(StyleTag.CASUAL, StyleTag.URBAN, StyleTag.CLASSIC,StyleTag.ALL),
                price = "R$ 102,88"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/05/11/0f/1715377920b0126e5f20bc09f5b644072328d33fa9.webp",
                name = "Camisa Social Masculina Manga Longa",
                purchaseLink = "https://br.shein.com/Men-Shirts-p-35387911.html",
                category = TagTypeClothes.SHIRTS,
                tags = listOf(StyleTag.FORMAL, StyleTag.ALL),
                price = "R$ 54,99"
            ),
            ClothingItem(
                imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/11/12/e6/1731413624c3341e72f036f0a25f8a12752a8de9f9.webp",
                name = "Camisa Camiseta Oversized Gola Alta Streetwear",
                purchaseLink = "https://br.shein.com/Shirt-Oversized-T-Shirt-Turtleneck-Streetwear-Men-And-Women-100-Cotton-Urban-Style-For-Training-And-Everyday-Black-And-Off-Whte-Printed-INVISIBLE-p-47641871.html",
                category = TagTypeClothes.T_SHIRTS,
                tags = listOf(StyleTag.URBAN, StyleTag.TREND, StyleTag.ALL),
                price = "R$ 28,90"
            )

            // Você pode adicionar quantos quiser aqui
        )
    }