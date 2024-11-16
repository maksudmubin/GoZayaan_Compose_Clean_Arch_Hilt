package com.mubin.gozayaan.base.utils.executionlocker

import android.os.Handler
import android.os.Looper
import android.util.Log

internal object ExecutionLocker {
    private const val LOGGER = "IsExecutionLocked -> "

    // Hold the state of locking
    @Volatile
    private var isLockingEnabled: Boolean = false

    // Get locking status
    internal val isLocked: Boolean
        get() = isLockingEnabled

    /**
     * This method is responsible to enable locking for the given time,
     * after the given time is over, release the locking state.
     *
     * If any error occur, release the locking state.
     *
     * @param delayMillis, given time to hold the locking state in millis [default is 2 sec]
     */
    internal fun lockExecution(delayMillis: Long = 2000) {
        try {
            isLockingEnabled = true
            log(isLockingEnabled)
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    isLockingEnabled = false
                    log(isLockingEnabled)
                },
                delayMillis
            )
        } catch (e: Exception) {
            e.printStackTrace()
            isLockingEnabled = false
        }
    }

    private fun log(status: Boolean) {
        Log.e(LOGGER, "log: $status")
    }
}