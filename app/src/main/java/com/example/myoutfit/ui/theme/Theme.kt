package com.example.myoutfit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(
    background = Color(0xFF1C1C1E), // Fundo geral
    surface = Color(0xFF2C2C2E),    // Cartões, caixas etc.
    primary = Color(0xFF2196F3),    // Azul principal
    secondary = Color(0xFF9E9E9E),  // Cinza secundário
    onBackground = Color.White,     // Texto sobre o fundo
    onSurface = Color.White,        // Texto sobre cartões
    onPrimary = Color.Black,        // Texto em cima do botão azul
    error = Color(0xFFFF5370)       // Cor de erro
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

val DefaultTypography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)

@Composable
fun MyOutfitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,  // Passa a variável de cor uma vez
        typography = DefaultTypography,
        shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(24.dp)
        ),
        content = content
    )
}