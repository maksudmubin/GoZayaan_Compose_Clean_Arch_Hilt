package com.mubin.triphaven.base.application

import android.app.Application
import com.example.core.utils.logger.GzLogger
import dagger.hilt.android.HiltAndroidApp

/**
 * The MainApp class serves as the entry point for the application.
 * Annotated with @HiltAndroidApp, it sets up Dagger Hilt's dependency injection framework.
 */
@HiltAndroidApp
class MainApp : Application() {

    /**
     * Called when the application is starting, before any other application objects have been created.
     * Override this method to perform one-time setup operations.
     */
    override fun onCreate() {
        super.onCreate()
        // Log a message indicating that the application has been initialized
        GzLogger.d("MainApp", "Application has started")
    }
}