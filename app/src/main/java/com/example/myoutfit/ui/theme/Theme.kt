package com.example.myoutfit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    background = Color(0xFF1C1C1E), // Fundo geral
    surface = Color(0xFF2C2C2E),    // Cartões, caixas etc.
    primary = Color(0xFF2196F3),
    secondary = Color(0xFF9E9E9E),// Cores principais (você pode trocar)
    onBackground = Color.White,     // Cor do texto em cima do background
    onSurface = Color.White         // Cor do texto em cima dos cartões
)

@Composable
fun MyOutfitTheme(
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else DarkColorScheme, // Por enquanto só dark
        typography = Typography,
        content = content
    )
}