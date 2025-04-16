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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MyOutfitHomeScreen() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomNavItem.entries.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedIndex) {
                0 -> HomeContent()
                1 -> SearchContent()
                2 -> FavoritesContent()
            }
        }
    }
}
data class Product(val imageUrl: String, val title: String, val link: String, val category: CategoryType)

enum class CategoryType {
    SUMMER, WINTER, TREND, WORKOUT
}

enum class BottomNavItem(val label: String, val icon: ImageVector) {
    HOME("Início", Icons.Default.Home),
    SEARCH("Busca", Icons.Default.Search),
    FAVORITES("Favoritos", Icons.Default.Favorite)
}

@Composable
fun HomeContent() {
    val products = listOf(
        Product(
            "https://img.ltwebstatic.com/images3_pi/2024/08/28/b7/1724811269f1b9f1b91b4f0b2dc34b7f2aca60c8a5.webp",
            "Jaqueta Preta PU",
            "https://br.shein.com/SWAVVY-Men-s-Fashionable-Daily-Casual-PU-Leather-Fitted-Long-Sleeve-Men-Jacket-Urban-Black-Jacket-For-Friends-Husband-Boyfriend-Gifts-p-41641968.html?mallCode=1&imgRatio=3-4",
            CategoryType.WINTER
        ),
        Product(
            "https://img.ltwebstatic.com/images3_spmp/2024/10/03/d3/172796272339daa9f54e33fe4bff473d187a24f118_thumbnail_336x.webp",
            "Camiseta Oversized",
            "https://br.shein.com/Men-T-Shirts-p-25143085.html",
            CategoryType.TREND
        ),
        Product(
            "https://img.ltwebstatic.com/images3_spmp/2023/06/12/16865104766b5b3b261d29d57196290c691452416f.webp",
            "Jaqueta Jeans",
            "https://br.shein.com/Men-Denim-Jackets-p-14722960.html",
            CategoryType.WINTER
        ),
        Product(
            "https://img.ltwebstatic.com/images3_pi/2024/03/06/59/17097300463527cb3179a7e75552444fd907ba6b23.webp",
            "Calça Alfaiataria",
            "https://br.shein.com/Manfinity-Homme-Men-s-Casual-Solid-Color-Slant-Pocket-Drawstring-Pants-Straight-Leg-Long-Slacks-Plain-Going-Out-p-3115655.html",
            CategoryType.TREND
        ),
        Product(
            "https://img.ltwebstatic.com/images3_spmp/2023/09/02/9a/1693662275a6500efb50040557607c45a67aa3b08c.webp",
            "Regata Canelada",
            "https://br.shein.com/Men-Tank-Tops-p-22602261.html",
            CategoryType.SUMMER
        ),
        Product(
            "https://img.ltwebstatic.com/images3_spmp/2025/02/19/4c/17399750382ff6a6719daecabbd80697b67f8fc201_square.webp",
            "Camisa Dry Fit",
            "https://br.shein.com/Men-s-Short-Sleeve-Dry-Fit-Gym-Fitness-T-Shirt-Lisa-Plus-Size-Zoo-p-48405365.html",
            CategoryType.WORKOUT
        ),
        Product(
            "https://img.ltwebstatic.com/images3_spmp/2024/08/17/97/1723836643d25ffdb991394f79b4bce2ac1d8f3419_thumbnail_336x.webp",
            "Camisa Oversized Academia Fit",
            "https://br.shein.com/Oversized-Shirt-Gym-Fashion-Fit-Bodybuilding-p-40991466.html",
            CategoryType.WORKOUT
        ),
        Product(
            "https://img.ltwebstatic.com/gspCenter/goodsImage/2023/1/31/4832058447_1029674/00ABECC87B4CB31F239FC5CE93594C71.webp",
            "Slim Fitness SLM Camiseta UV Masculina Proteção Solar",
            "https://br.shein.com/Slim-Fitness-SLM-Men-T-Shirts-Tanks-p-12893370.html",
            CategoryType.WORKOUT
        ),
        Product(
            "https://img.ltwebstatic.com/images3_spmp/2024/12/19/80/17346196374ea253869f05097f48bd52ce0311635f.webp",
            "Camisa de Linho Manga curta Social",
            "br.shein.com/Linen-Shirt-Short-Sleeve-Formal-Summer-Fresh-Italian-Collar-Elegant-In-Visco-Linen-p-50917714.html",
            CategoryType.SUMMER
        ),
        Product(
            "https://img.ltwebstatic.com/images3_spmp/2024/01/30/1b/1706556936ae19911a343e9b7ba6a9732a033a3107.webp",
            "Calça Masculina Cargo Sarja Skatista",
            "https://br.shein.com/Men-Jeans-p-30021128.html",
            CategoryType.TREND
        ),

        Product(
            "https://img.ltwebstatic.com/images3_spmp/2024/02/02/28/17068460474610b8f45f7cdfb741129b0b4b5abaa4.webp",
            "Bermuda Tecido Sarja",
            "br.shein.com/Men-Shorts-p-13972715.html",
            CategoryType.SUMMER
        ),

    )

    val categoriesToShow = listOf(
        Pair("Tendências", CategoryType.TREND),
        Pair("Para o Verão", CategoryType.SUMMER),
        Pair("Para o Inverno", CategoryType.WINTER),
        Pair("Para o seu Treino", CategoryType.WORKOUT),
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        categoriesToShow.forEach { (title, categoryType) ->
            val filteredProducts = products.filter { it.category == categoryType }

            if (filteredProducts.isNotEmpty()) {
                Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(filteredProducts) { product ->
                        TrendCard(
                            imageUrl = product.imageUrl,
                            title = product.title,
                            link = product.link
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun TrendCard(imageUrl: String, title: String, link: String) {
    val context = LocalContext.current

    ElevatedCard(
        modifier = Modifier
            .width(160.dp)
            .height(240.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                context.startActivity(intent)
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(160.dp)
                    .height(160.dp)

            )
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

data class Category(val name: String, val imageUrl: String)

@Composable
fun SearchContent() {
    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf(
        Category("Jaquetas", "https://img.icons8.com/?size=100&id=1186&format=png&color=000000"),
        Category("Camisetas", "https://img.icons8.com/?size=100&id=105819&format=png&color=000000"),
        Category("Calças", "https://img.icons8.com/?size=100&id=1172&format=png&color=000000"),
        Category("Tênis", "https://img.icons8.com/?size=100&id=69628&format=png&color=000000"),
        Category("Acessórios", "https://img.icons8.com/?size=100&id=DMhtuPky5Yql&format=png&color=000000"),
        Category("Camisas", "https://img.icons8.com/?size=100&id=1169&format=png&color=000000"),
        Category("Shorts", "https://img.icons8.com/?size=100&id=1174&format=png&color=000000"),

    )

    categories.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar roupas...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Ícone de busca")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // Deixei 3 por linha pra encaixar melhor cards menores
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(categories) { category ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp) // Reduzido pela metade
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        AsyncImage(
                            model = category.imageUrl,
                            contentDescription = category.name,
                            modifier = Modifier
                                .size(48.dp) // Ícone menor
                        )
                        Text(
                            text = category.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoritesContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Seus favoritos aparecerão aqui", fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyOutfitHomeScreen() {
    MyOutfitHomeScreen()
}