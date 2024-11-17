package com.mubin.gozayaan.base.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.mubin.gozayaan.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Executes a given suspendable block of code (`body`) within a specific coroutine context
 * (either `Dispatchers.Main` or `Dispatchers.IO`), and handles exceptions gracefully by returning `null` if an exception occurs.
 *
 * @param T The return type of the suspendable block.
 * @param shouldUseMainScope Determines whether the block should be executed on the `Main` dispatcher. Defaults to `true`.
 * @param body The suspendable block of code to execute.
 * @return The result of the block if successful, or `null` if an exception is thrown.
 */
suspend inline fun <T> executeBodyOrReturnNullSuspended(
    shouldUseMainScope: Boolean = true,
    crossinline body: suspend CoroutineScope.() -> T
): T? {
    return withContext(if (shouldUseMainScope) Dispatchers.Main else Dispatchers.IO) {
        return@withContext try {
            body.invoke(this)
        } catch (e: Exception) {
            // Log and handle the exception; return null as a fallback
            e.printStackTrace()
            null
        }
    }
}

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
