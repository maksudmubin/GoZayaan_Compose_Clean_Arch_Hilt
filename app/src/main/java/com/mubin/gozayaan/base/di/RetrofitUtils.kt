package com.mubin.gozayaan.base.di

import android.content.Context
import com.google.gson.Gson
import com.itkacher.okprofiler.BuildConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.mubin.gozayaan.base.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * RetrofitUtils is a Dagger Hilt module for providing Retrofit instances and related utilities.
 * This module is installed in the SingletonComponent, meaning the dependencies it provides
 * will have a singleton lifecycle tied to the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitUtils {

    /**
     * Creates an HTTP response cache for network calls.
     *
     * @param application The application context for accessing the cache directory.
     * @return A Cache instance with a predefined size of 10MB.
     */
    private fun createCache(application: Context): Cache {
        val cacheSize = 10L * 1024L * 1024L // 10MB
        return Cache(File(application.cacheDir, "${application.packageName}.cache"), cacheSize)
    }

    /**
     * Configures and creates an OkHttpClient instance.
     *
     * @param cache An optional Cache instance for HTTP response caching.
     * @return A configured OkHttpClient instance.
     */
    private fun createOkHttpClient(cache: Cache?): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                // Add profiler interceptor only in debug builds
                addInterceptor(OkHttpProfilerInterceptor())
            }
            cache(cache)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(1, TimeUnit.MINUTES)
            connectTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    /**
     * Provides a Retrofit instance configured with GsonConverterFactory and an OkHttpClient.
     *
     * @param context The application context for creating the HTTP cache.
     * @return A configured Retrofit instance.
     */
    @Provides
    fun retrofitInstance(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(createCache(context)))
            .build()
    }
}