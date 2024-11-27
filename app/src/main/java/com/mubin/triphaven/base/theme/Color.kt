package com.mubin.triphaven.base.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Background = Color(0xff1D2026)
val Surface = Color(0xff2D303A)

val gradient = Brush.linearGradient(
    colors = listOf(Color(0xFFF88264), Color(0xFFFFE3C5)), // Define colors for the gradient
    start = Offset(0f, 0f),  // Starting position of the gradient (top-left corner)
    end = Offset(1000f, 1000f)    // Ending position of the gradient (bottom-right corner)
)