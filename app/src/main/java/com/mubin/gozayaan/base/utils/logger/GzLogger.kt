package com.mubin.gozayaan.base.utils.logger

import android.util.Log
import com.mubin.gozayaan.BuildConfig

/**
 * APLogger is a centralized logging utility class.
 * It wraps Android's Log class and provides additional functionality for formatted logging,
 * logging with method and line number, and ensuring logging only happens in debug builds.
 */
object GzLogger {

    /**
     * Logs a debug message. Only logs if the build is in debug mode.
     * @param tag The tag for the log message. Defaults to "APLogger".
     * @param message The message to log.
     */
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    /**
     * Logs an info message. Only logs if the build is in debug mode.
     * @param tag The tag for the log message. Defaults to "APLogger".
     * @param message The message to log.
     */
    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    /**
     * Logs a warning message. Only logs if the build is in debug mode.
     * @param tag The tag for the log message. Defaults to "APLogger".
     * @param message The message to log.
     */
    fun w(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message)
        }
    }

    /**
     * Logs an error message. Only logs if the build is in debug mode.
     * Optionally, a Throwable can be logged along with the message.
     * @param tag The tag for the log message. Defaults to "APLogger".
     * @param message The message to log.
     * @param throwable Optional throwable to log along with the message.
     */
    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            if (throwable != null) {
                Log.e(tag, message, throwable)
            } else {
                Log.e(tag, message)
            }
        }
    }

    /**
     * Logs a verbose message. Only logs if the build is in debug mode.
     * @param tag The tag for the log message. Defaults to "APLogger".
     * @param message The message to log.
     */
    fun v(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }

    /**
     * Logs a formatted message, where the message is created using String.format().
     * Only logs if the build is in debug mode.
     * @param tag The tag for the log message. Defaults to "APLogger".
     * @param format The format string (same as String.format()).
     * @param args The arguments to format the message with.
     */
    fun logWithFormat(tag: String, format: String, vararg args: Any) {
        if (BuildConfig.DEBUG) {
            val formattedMessage = String.format(format, *args)
            Log.d(tag, formattedMessage)
        }
    }

    /**
     * Logs a debug message along with the method name and line number of where the log was called.
     * Useful for pinpointing the source of a log message.
     * Only logs if the build is in debug mode.
     * @param tag The tag for the log message. Defaults to "APLogger".
     * @param message The message to log.
     */
    fun logDebugWithLocation(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            val stackTrace = Throwable().stackTrace
            if (stackTrace.size > 1) {
                val element = stackTrace[1]
                val location = "${element.fileName}:${element.lineNumber}"
                Log.d(tag, "[$location] $message")
            } else {
                Log.d(tag, message)
            }
        }
    }
}


