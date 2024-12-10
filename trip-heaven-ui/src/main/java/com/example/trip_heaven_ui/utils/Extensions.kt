package com.example.trip_heaven_ui.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.trip.heaven.ui.R

// Composable Extension Fonts

/**
 * Lazy initialization of the Rubik Bold font family.
 */
val RubikFontBold by lazy { FontFamily(Font(R.font.rubik_bold)) }

/**
 * Lazy initialization of the Rubik Medium font family.
 */
val RubikFontMedium by lazy { FontFamily(Font(R.font.rubik_medium)) }

/**
 * Lazy initialization of the Rubik Regular font family.
 */
val RubikFontRegular by lazy { FontFamily(Font(R.font.rubik_regular)) }

/**
 * Lazy initialization of the Gilroy Medium font family.
 */
val GilroyFontMedium by lazy { FontFamily(Font(R.font.gilroy_medium)) }
