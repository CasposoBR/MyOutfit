package com.example.myoutfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.myoutfit.ui.theme.MyOutfitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyOutfitTheme {
                AuthScreen() //tela de login e cadastro
            }
        }
    }
}

@Composable
fun AuthScreen() {
    // Implemente a tela aqui
}