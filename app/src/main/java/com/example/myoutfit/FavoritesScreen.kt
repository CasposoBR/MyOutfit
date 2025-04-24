package com.example.myoutfit

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.grid.items

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by viewModel.favoriteItems.collectAsState()
    val context = LocalContext.current // ✅ Obtenha o contexto AQUI, fora da lambda

    if (favorites.isEmpty()) {
        FavoritesContent(navController)
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(favorites) { item ->
                ProductCard(
                    product = item,
                    onClick = {
                        // Usando o contexto que já foi obtido fora da lambda
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.purchaseLink))
                        context.startActivity(intent)
                    },
                    onToggleFavorite = { viewModel.toggleFavorite(item) }
                )
            }
        }
    }
}