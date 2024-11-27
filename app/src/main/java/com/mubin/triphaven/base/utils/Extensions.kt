package com.mubin.triphaven.base.utils

import android.content.Context
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.mubin.triphaven.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Currency

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

/**
 * Creates an [ImageRequest] to load an image from the provided URL.
 *
 * This function constructs a Coil [ImageRequest] with the following configurations:
 * - The image source is the provided URL (`url`).
 * - It runs the image request on a background IO thread using the `Dispatchers.IO` dispatcher.
 * - Caching strategies are applied for both memory and disk caches:
 *   - The memory cache key and disk cache key are both set to the image URL (`url`).
 *   - Caching is enabled for both memory and disk caches.
 * - In case of errors or fallback scenarios, a default placeholder image (`ic_no_image_available`) is used.
 * - Crossfade animation is enabled when loading the image.
 *
 * @param context The context used to build the [ImageRequest].
 * @param url The URL of the image to be loaded.
 *
 * @return An [ImageRequest] that can be passed to Coil's image loading mechanisms.
 *
 * @see ImageRequest
 */
fun createImageRequest(context: Context, url: String?): ImageRequest {
    return ImageRequest.Builder(context)
        .data(data = url)
        .dispatcher(dispatcher = Dispatchers.IO)
        .memoryCacheKey(key = url)
        .diskCacheKey(key = url)
        .error(drawableResId = R.drawable.ic_no_image_available)
        .fallback(drawableResId = R.drawable.ic_no_image_available)
        .crossfade(enable = true)
        .diskCachePolicy(policy = CachePolicy.ENABLED)
        .memoryCachePolicy(policy = CachePolicy.ENABLED)
        .build()
}

/**
 * Extension function to get the currency symbol for a given currency code.
 *
 * @param currencyCode The 3-letter ISO 4217 currency code (e.g., "USD", "INR").
 * @return The corresponding currency symbol (e.g., "$", "₹").
 */
fun String.getCurrencySymbol(): String {
    return try {
        val currency = Currency.getInstance(this) // Get the currency instance for the code
        currency.symbol // Return the currency symbol
    } catch (e: IllegalArgumentException) {
        // Return an empty string if the currency code is invalid
        ""
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
