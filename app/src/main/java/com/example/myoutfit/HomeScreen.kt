
package com.example.myoutfit

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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

enum class BottomNavItem(val label: String, val icon: ImageVector) {
    HOME("Início", Icons.Default.Home),
    SEARCH("Busca", Icons.Default.Search),
    FAVORITES("Favoritos", Icons.Default.Favorite)
}

@Composable
fun HomeContent(
    navController: NavHostController,
    viewModel: ClothingViewModel = hiltViewModel()

) {
    val allProducts by viewModel.allItems.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Seções baseadas em estilo
        SectionTitle("Tendências")
        CategorySection(allProducts.filter { it.tags.contains(StyleTag.TREND) }, navController, viewModel)

        SectionTitle("Para o seu verão")
        CategorySection(allProducts.filter { it.tags.contains(StyleTag.SUMMER) }, navController, viewModel)

        SectionTitle("Para o seu inverno")
        CategorySection(allProducts.filter { it.tags.contains(StyleTag.WINTER) }, navController, viewModel)

        SectionTitle("Para o seu treino")
        CategorySection(allProducts.filter { it.tags.contains(StyleTag.WORKOUT) }, navController, viewModel)

        SectionTitle("Se destaque")
        CategorySection(allProducts.filter { it.tags.contains(StyleTag.FORMAL) }, navController, viewModel)


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
fun CategorySection(
    products: List<ClothingItem>,
    navController: NavHostController,
    viewModel: ClothingViewModel
) {
    val context = LocalContext.current

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(product.purchaseLink))
                    context.startActivity(intent)
                },
                onToggleFavorite = {
                    viewModel.toggleFavorite(it)
                }
            )
        }
    }

    Spacer(Modifier.height(24.dp))
}


@Composable
fun ProductCard(
    product: ClothingItem,
    onClick: () -> Unit,
    onToggleFavorite: (ClothingItem) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .width(160.dp)
            .height(260.dp)
            .clickable { onClick() }
    ) {
        Box {
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
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = "R$ ${product.price}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            IconButton(
                onClick = { onToggleFavorite(product) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = if (product.isFavorite) Icons.Default.Star else Icons.Default.Star,
                    contentDescription = "Favoritar",
                    tint = if (product.isFavorite) Color.Yellow else Color.Gray
                )
            }
        }
    }
}

@Composable
fun SearchContent(
    navController: NavHostController,
    viewModel: ClothingViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredProducts by viewModel.getProductsByQuery(searchQuery).collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Pesquise uma peça...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = filteredProducts.isNotEmpty() || searchQuery.isBlank(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredProducts) { product ->
                    val context = LocalContext.current
                    ProductCard(
                        product = product,
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(product.purchaseLink))
                            context.startActivity(intent)
                        },
                        onToggleFavorite = {
                            viewModel.toggleFavorite(it)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun FavoritesContent(
    navController: NavHostController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by viewModel.favoriteItems.collectAsState()
    val context = LocalContext.current // ✅ PEGAR O CONTEXTO AQUI

    if (favorites.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Seus favoritos aparecerão aqui", fontSize = 16.sp)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(favorites) { product ->
                ProductCard(
                    product = product,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(product.purchaseLink))
                        context.startActivity(intent)
                    },
                    onToggleFavorite = {
                        viewModel.removeFavorite(product)
                    }
                )
            }
        }
    }
}




