package com.example.myoutfit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    background = Color(0xFF121212),       // Fundo mais uniforme e profundo
    surface = Color(0xFF1E1E1E),          // Cartões e caixas levemente destacadas
    primary = Color(0xFF64B5F6),          // Azul suave (não tão saturado) – moderno e calmo
    secondary = Color(0xFFB0BEC5),        // Cinza azulado elegante (subtítulos, ícones)
    onBackground = Color(0xFFF5F5F5),     // Branco suave, não estourado
    onSurface = Color(0xFFE0E0E0),        // Texto claro sobre cartões
    onPrimary = Color.Black,              // Preto sobre azul claro
    error = Color(0xFFEF5350)             // Vermelho moderno, mais suave que o anterior
)

private val LightColorScheme = lightColorScheme(
    background = Color(0xFFFDFDFD),       // Branco quase puro, com toque suave
    surface = Color(0xFFFFFFFF),          // Branco puro para cartões e campos
    primary = Color(0xFF0D47A1),          // Azul escuro sofisticado (profundo)
    secondary = Color(0xFF757575),        // Cinza neutro elegante
    onBackground = Color(0xFF121212),     // Preto suave para melhor contraste
    onSurface = Color(0xFF1E1E1E),        // Levemente mais claro para cartões
    onPrimary = Color.White,              // Branco para texto sobre o azul escuro
    error = Color(0xFFD32F2F)             // Vermelho discreto para erros
)


@Composable
fun MyOutfitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(24.dp)
        ),
        content = content
    )
}