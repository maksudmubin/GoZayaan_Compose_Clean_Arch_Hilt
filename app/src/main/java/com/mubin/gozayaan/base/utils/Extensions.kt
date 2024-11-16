package com.mubin.gozayaan.base.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.mubin.gozayaan.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val getDefaultText = "---"

suspend inline fun <T> executeBodyOrReturnNullSuspended(
    shouldUseMainScope: Boolean = true,
    crossinline body: suspend CoroutineScope.() -> T
): T? {
    return withContext(if (shouldUseMainScope) Dispatchers.Main else Dispatchers.IO) {
        return@withContext try {
            body.invoke(this)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

//Composable Extension
val RubikFontBold by lazy { FontFamily(Font(R.font.rubik_bold)) }
val RubikFontMedium by lazy { FontFamily(Font(R.font.rubik_medium)) }
val RubikFontRegular by lazy { FontFamily(Font(R.font.rubik_regular)) }
val GilroyFontMedium by lazy { FontFamily(Font(R.font.gilroy_medium)) }
