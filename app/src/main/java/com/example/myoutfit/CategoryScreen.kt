package com.example.myoutfit

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController


@Composable
fun CategoryScreen(
    categoryName: String,
    categoryViewModel: CategoryViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current // 👈 PEGUE o contexto AQUI
    val products by categoryViewModel.products.collectAsState()
    val isLoading by categoryViewModel.isLoading.collectAsState()
    val error by categoryViewModel.error.collectAsState()

    LaunchedEffect(categoryName) {
        categoryViewModel.loadProductsByCategory(categoryName)
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else if (error != null) {
        Text(text = error ?: "Erro desconhecido", color = Color.Red)
    } else {
        LazyColumn {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(product.purchaseLink))
                        context.startActivity(intent)
                    },
                    onToggleFavorite = { /* você pode deixar vazio por enquanto */ }
                )
            }
        }
    }
}
