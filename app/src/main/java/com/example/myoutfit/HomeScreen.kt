package com.example.myoutfit
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Estilos Populares", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf("Star Boy", "Old Money", "Skatista").forEach { estilo ->
                AssistChip(
                    onClick = { /* TODO: Navegar para estilo */ },
                    label = { Text(estilo) }
                )
            }
        }

        Spacer(Modifier.height(32.dp))
        Text("Tendências", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(12.dp))

        // Aqui futuramente você pode colocar um LazyRow com os cards das roupas
        // Por exemplo: LazyRow { items(tendencias) { ... } }
        Text("Em breve: Lista de tendências...")
    }
}

@Composable
fun SearchContent() {
    val categorias = listOf("Camisas", "Jaquetas", "Calças", "Shorts", "Sapatos", "Peça Única", "Praia", "Fitness")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(categorias) { categoria ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(categoria, fontWeight = FontWeight.Medium)
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