package com.example.myoutfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun CategoryScreen(categoryType: TagTypeClothes, viewModel: ClothingViewModel, navController: NavHostController) {
    val products = viewModel.getProductsByCategory(categoryType).collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Itens da categoria: ${categoryType.name}",
            style = MaterialTheme.typography.titleLarge
        )

        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(products.value) { product ->
                ProductCard(
                    product = product,
                    onClick = {
                        // Lógica para navegar ou abrir os detalhes do produto
                    }
                )
            }
        }

        Button(onClick = {
            // Navegar de volta
            navController.popBackStack()
        }) {
            Text("Voltar")
        }
    }
}