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

enum class BottomNavItem(val label: String, val icon: ImageVector) {
    HOME("Início", Icons.Default.Home),
    SEARCH("Busca", Icons.Default.Search),
    FAVORITES("Favoritos", Icons.Default.Favorite)
}

@Composable
fun HomeContent() {
    val trends = listOf(
        Triple(
            "https://img.ltwebstatic.com/images3_pi/2023/11/14/0b/1699949722745bdb33179b63690fd02cfdd61fd1b4_thumbnail_720x.jpg",
            "Jaqueta Preta PU",
            "https://br.shein.com/SWAVVY-Men-s-Fashionable-Daily-Casual-PU-Leather-Fitted-Long-Sleeve-Men-Jacket-Urban-Black-Jacket-For-Friends-Husband-Boyfriend-Gifts-p-41641968.html"
        ),
        Triple(
            "https://img.ltwebstatic.com/images3_pi/2023/09/28/3e/1695897567ae01b13e7a184b3b2adff6c81a850517_thumbnail_720x.jpg",
            "Camiseta Oversized",
            "https://br.shein.com/Men-T-Shirts-p-25143085.html"
        ),
        Triple(
            "https://img.ltwebstatic.com/images3_pi/2022/09/27/16642676311e0c5aaf104bf0474c2b47654c4a0276_thumbnail_720x.jpg",
            "Jaqueta Jeans",
            "https://br.shein.com/Men-Denim-Jackets-p-14722960.html"
        ),
        Triple(
            "https://img.ltwebstatic.com/images3_pi/2022/08/10/1660109833f7cb7542d3b1724a67c23309fef9b56f_thumbnail_720x.jpg",
            "Calça Alfaiataria",
            "https://br.shein.com/Manfinity-Homme-Men-s-Casual-Solid-Color-Slant-Pocket-Drawstring-Pants-Straight-Leg-Long-Slacks-Plain-Going-Out-p-3115655.html"
        )
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Estilos Populares", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Star Boy", "Old Money", "Skatista").forEach { style ->
                AssistChip(onClick = { /* navigate to style */ }, label = { Text(style) })
            }
        }
        Spacer(Modifier.height(24.dp))
        Text("Tendências", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(trends) { (image, title, link) ->
                TrendCard(
                    imageUrl = image,
                    title = title,
                    link = link
                )
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
                modifier = Modifier
                    .fillMaxWidth()
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
    val categories = listOf(
        Category("Jaquetas", "https://i.imgur.com/k8z5vAY.jpg"),
        Category("Camisas Oversized", "https://i.imgur.com/VQb1M5f.jpg"),
        Category("Calças", "https://i.imgur.com/n7NnE5V.jpg"),
        Category("Tênis", "https://i.imgur.com/I5HR82T.jpg"),
        Category("Acessórios", "https://i.imgur.com/NkTCuMu.jpg"),
        Category("Peça Única", "https://i.imgur.com/WSNxJ2E.jpg")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(categories) { category ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    AsyncImage(
                        model = category.imageUrl,
                        contentDescription = category.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                    )
                    Text(
                        text = category.name,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(8.dp)
                    )
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