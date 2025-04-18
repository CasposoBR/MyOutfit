package com.example.myoutfit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun CategoryScreen(
    category: String,
    categoryType: TagTypeClothes,
    viewModel: CategoryViewModel,
    navController: NavHostController,
    onBack: () -> Boolean = { navController.popBackStack() }
) {
    // Carregar os produtos quando a tela for composta
    LaunchedEffect(categoryType) {
        viewModel.loadProductsByCategory(categoryType)
    }

    val products = viewModel.products.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = category.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (isLoading) {
            Text("Carregando...", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (error != null) {
            Text(error, modifier = Modifier.align(Alignment.CenterHorizontally), color = MaterialTheme.colorScheme.error)
        } else if (products.isEmpty()) {
            Text("Nenhum produto encontrado.", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
                    ProductCard(product = product)
                }
            }
        }
    }
}