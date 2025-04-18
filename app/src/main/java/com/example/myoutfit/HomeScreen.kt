// TODO: Organizar categorias corretamente
package com.example.myoutfit

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

@Composable
fun MyOutfitHomeScreen(navController: NavHostController) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(selectedIndex = selectedIndex, onItemClick = { selectedIndex = it })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedIndex) {
                0 -> HomeContent(navController)
                1 -> SearchContent(navController)
                2 -> FavoritesContent(navController)
            }
        }
    }
}

@Composable
fun BottomNavBar(selectedIndex: Int, onItemClick: (Int) -> Unit) {
    NavigationBar {
        BottomNavItem.entries.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedIndex == index,
                onClick = { onItemClick(index) }
            )
        }
    }
}

enum class CategoryType {
    SUMMER, WINTER, TREND, WORKOUT, CASUAL, FORMAL, URBAN,
    SHORTS, TSHIRT, JACKETS, PANTHS, SHOES, ALL
}

enum class BottomNavItem(val label: String, val icon: ImageVector) {
    HOME("Início", Icons.Default.Home),
    SEARCH("Busca", Icons.Default.Search),
    FAVORITES("Favoritos", Icons.Default.Favorite)
}

@Composable
fun HomeContent(navController: NavHostController) {
    val products = getProducts() // Obtendo a lista de produtos
    val categories = TagTypeClothes.entries.toTypedArray() // Obtendo todas as categorias de roupas (do enum TagTypeClothes)

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Seção de "Para o seu verão", "Para o seu inverno", "Tendências"
        // Exibindo essas vitrine de maneira destacada

        SectionTitle("Tendências")
        val trendProducts = products.filter { product -> product.tags.contains(StyleTag.TREND) }
        CategorySection(title = "Tendências", products = trendProducts, navController = navController)


        SectionTitle("Para o seu verão")
        val summerProducts = products.filter { product -> product.tags.contains(StyleTag.SUMMER) }
        CategorySection(title = "Para o seu verão", products = summerProducts, navController = navController)

        SectionTitle("Para o seu inverno")
        val winterProducts = products.filter { product -> product.tags.contains(StyleTag.WINTER) }
        CategorySection(title = "Para o seu inverno", products = winterProducts, navController = navController)

        SectionTitle("Para o seu treino")
        val workoutProducts = products.filter { product -> product.tags.contains(StyleTag.WORKOUT) }
        CategorySection(title = "Para o seu treino", products = workoutProducts, navController = navController)

        SectionTitle("Se destaque")
        val formalProducts = products.filter { product -> product.tags.contains(StyleTag.FORMAL) }
        CategorySection(title = "Para o seu treino", products = formalProducts, navController = navController)

        // Exibindo as categorias baseadas no TagTypeClothes
        categories.forEach { category ->
            val filteredProducts = products.filter { product ->
                product.tags.any { tag -> tag.name == category.name }
            }

            if (filteredProducts.isNotEmpty()) {
                CategorySection(title = category.name, products = filteredProducts, navController = navController)
            }
        }
    }
}



@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun CategorySection(title: String, products: List<ClothingItem>, navController: NavHostController) {
    Column {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(products) { product ->
                ProductCard(product = product)
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun ProductCard(product: ClothingItem) {
    val context = LocalContext.current

    ElevatedCard(
        modifier = Modifier
            .width(160.dp)
            .height(240.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(product.purchaseLink))
                context.startActivity(intent)
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(160.dp)
                    .height(160.dp)
            )
            Text(
                text = product.name,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun SearchContent(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }

    val categories = getSearchCategories()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Pesquise uma peça...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (searchQuery.isNotBlank()) {
            SearchResults(searchQuery, categories, navController)
        } else {
            CategorySelection(categories, navController)
        }
    }
}

@Composable
fun SearchResults(query: String, categories: List<Category>, navController: NavHostController) {
    val filteredCategories = categories.filter {
        it.name.contains(query, ignoreCase = true)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(filteredCategories) { category ->
            CategoryCard(category = category, navController = navController)
        }
    }
}

@Composable
fun CategorySelection(categories: List<Category>, navController: NavHostController) {
    Text("Escolha uma categoria:", textAlign = TextAlign.Center)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(categories) { category ->
            CategoryCard(category = category, navController = navController)
        }
    }
}

@Composable
fun CategoryCard(category: Category, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Navegar para a tela de categoria com base no nome da categoria
                navController.navigate("category/${category.name}")
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = category.imageUrl,
            contentDescription = category.name,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FavoritesContent(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Seus favoritos aparecerão aqui", fontSize = 16.sp)
    }
}

data class Category
    (val name: String, val imageUrl: String)


fun getProducts(): List<ClothingItem> {
    return listOf(
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_pi/2024/08/28/b7/1724811269f1b9f1b91b4f0b2dc34b7f2aca60c8a5.webp",
            name = "Jaqueta Preta PU",
            purchaseLink = "https://br.shein.com/SWAVVY-Men-s-Fashionable-Daily-Casual-PU-Leather-Fitted-Long-Sleeve-Men-Jacket-Urban-Black-Jacket-For-Friends-Husband-Boyfriend-Gifts-p-41641968.html?mallCode=1&imgRatio=3-4",
            category = TagTypeClothes.JACKETS,
            tags = listOf(StyleTag.CASUAL, StyleTag.WINTER, StyleTag.URBAN),
            price = "R$ 199,99"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/10/03/d3/172796272339daa9f54e33fe4bff473d187a24f118_thumbnail_336x.webp",
            name = "Camiseta Oversized",
            purchaseLink = "https://br.shein.com/Men-T-Shirts-p-25143085.html",
            category = TagTypeClothes.T_SHIRTS,
            tags = listOf(StyleTag.TREND, StyleTag.CASUAL),
            price = "R$ 79,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_spmp/2023/06/12/16865104766b5b3b261d29d57196290c691452416f.webp",
            name = "Jaqueta Jeans",
            purchaseLink = "https://br.shein.com/Men-Denim-Jackets-p-14722960.html",
            category = TagTypeClothes.JACKETS,
            tags = listOf(StyleTag.WINTER, StyleTag.CASUAL),
            price = "R$ 159,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_pi/2024/03/06/59/17097300463527cb3179a7e75552444fd907ba6b23.webp",
            name = "Calça Alfaiataria",
            purchaseLink = "https://br.shein.com/Manfinity-Homme-Men-s-Casual-Solid-Color-Slant-Pocket-Drawstring-Pants-Straight-Leg-Long-Slacks-Plain-Going-Out-p-3115655.html",
            category = TagTypeClothes.PANTS,
            tags = listOf(StyleTag.TREND, StyleTag.FORMAL),
            price = "R$ 159,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_spmp/2023/09/02/9a/1693662275a6500efb50040557607c45a67aa3b08c.webp",
            name = "Regata Canelada",
            purchaseLink = "https://br.shein.com/Men-Tank-Tops-p-22602261.html",
            category = TagTypeClothes.T_SHIRTS,
            tags = listOf(StyleTag.SUMMER, StyleTag.CASUAL),
            price = "R$ 159,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_spmp/2025/02/19/4c/17399750382ff6a6719daecabbd80697b67f8fc201_square.webp",
            name = "Camisa Dry Fit",
            purchaseLink = "https://br.shein.com/Men-s-Short-Sleeve-Dry-Fit-Gym-Fitness-T-Shirt-Lisa-Plus-Size-Zoo-p-48405365.html",
            category = TagTypeClothes.T_SHIRTS,
            tags = listOf(StyleTag.WORKOUT, StyleTag.CASUAL),
            price = "R$ 159,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/08/17/97/1723836643d25ffdb991394f79b4bce2ac1d8f3419_thumbnail_336x.webp",
            name = "Camisa Oversized Academia Fit",
            purchaseLink = "https://br.shein.com/Oversized-Shirt-Gym-Fashion-Fit-Bodybuilding-p-40991466.html",
            category = TagTypeClothes.T_SHIRTS,
            tags = listOf(StyleTag.WORKOUT, StyleTag.TREND),
            price = "R$ 159,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/gspCenter/goodsImage/2023/1/31/4832058447_1029674/00ABECC87B4CB31F239FC5CE93594C71.webp",
            name = "Slim Fitness SLM Camiseta UV Masculina Proteção Solar",
            purchaseLink = "https://br.shein.com/Slim-Fitness-SLM-Men-T-Shirts-Tanks-p-12893370.html",
            category = TagTypeClothes.SHIRTS,
            tags = listOf(StyleTag.WORKOUT, StyleTag.SUMMER),
            price = "R$ 159,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/12/19/80/17346196374ea253869f05097f48bd52ce0311635f.webp",
            name = "Camisa de Linho Manga curta Social",
            purchaseLink = "https://br.shein.com/Linen-Shirt-Short-Sleeve-Formal-Summer-Fresh-Italian-Collar-Elegant-In-Visco-Linen-p-50917714.html",
            category = TagTypeClothes.SHIRTS,
            tags = listOf(StyleTag.SUMMER, StyleTag.FORMAL),
            price = "R$ 159,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/01/30/1b/1706556936ae19911a343e9b7ba6a9732a033a3107.webp",
            name = "Calça Masculina Cargo Sarja Skatista",
            purchaseLink = "https://br.shein.com/Men-Jeans-p-30021128.html",
            category = TagTypeClothes.PANTS,
            tags = listOf(StyleTag.TREND, StyleTag.URBAN),
            price = "R$ 159,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/02/02/28/17068460474610b8f45f7cdfb741129b0b4b5abaa4.webp",
            name = "Bermuda Tecido Sarja",
            purchaseLink = "https://br.shein.com/Men-Shorts-p-13972715.html",
            category = TagTypeClothes.SHORTS,
            tags = listOf(StyleTag.SUMMER, StyleTag.CASUAL),
            price = "R$ 159,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_spmp/2025/03/17/98/1742169872c605f20beaacf7759ac2b836b3552f2e.webp",
            name = "Calça Jeans Preta Bag Balão",
            purchaseLink = "https://br.shein.com/Bag-Balloon-Jeans-Wide-Straight-Cut-Carpenter-UNISEX-p-61215286.html",
            category = TagTypeClothes.PANTS,
            tags = listOf(StyleTag.CASUAL, StyleTag.URBAN),
            price = "R$ 159,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/05/11/0f/1715377920b0126e5f20bc09f5b644072328d33fa9.webp",
            name = "Camisa Social Masculina Manga Longa",
            purchaseLink = "https://br.shein.com/Men-Shirts-p-35387911.html",
            category = TagTypeClothes.SHIRTS,
            tags = listOf(StyleTag.FORMAL),
            price = "R$ 159,90"
        ),
        ClothingItem(
            imageUrl = "https://img.ltwebstatic.com/images3_spmp/2024/11/12/e6/1731413624c3341e72f036f0a25f8a12752a8de9f9.webp",
            name = "Camisa Camiseta Oversized Gola Alta Streetwear",
            purchaseLink = "https://br.shein.com/Shirt-Oversized-T-Shirt-Turtleneck-Streetwear-Men-And-Women-100-Cotton-Urban-Style-For-Training-And-Everyday-Black-And-Off-Whte-Printed-INVISIBLE-p-47641871.html",
            category = TagTypeClothes.T_SHIRTS,
            tags = listOf(StyleTag.URBAN, StyleTag.TREND),
            price = "R$ 159,90"
        )
    )
}

fun getCategories(): List<TagTypeClothes> {
    return listOf(
        TagTypeClothes.SHORTS,
        TagTypeClothes.T_SHIRTS,
        TagTypeClothes.SHIRTS,
        TagTypeClothes.PANTS,
        TagTypeClothes.JACKETS,
        TagTypeClothes.ACCESSORIES,
        TagTypeClothes.SHOES
    )
}

fun getSearchCategories(): List<Category> {
    return listOf(
        Category("Jaquetas", "https://img.icons8.com/?size=100&id=1186&format=png&color=000000"),
        Category("Camisetas", "https://img.icons8.com/?size=100&id=105819&format=png&color=000000"),
        Category("Calças", "https://img.icons8.com/?size=100&id=1172&format=png&color=000000"),
        Category("Tênis", "https://img.icons8.com/?size=100&id=69628&format=png&color=000000"),
        Category("Acessórios", "https://img.icons8.com/?size=100&id=DMhtuPky5Yql&format=png&color=000000"),
        Category("Camisas", "https://img.icons8.com/?size=100&id=1169&format=png&color=000000"),
        Category("Shorts", "https://img.icons8.com/?size=100&id=1174&format=png&color=000000"),
        // Add more categories here...
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMyOutfitHomeScreen() {
    val fakeNavController = rememberNavController()
    MyOutfitHomeScreen(navController = fakeNavController)
}