package com.mubin.gozayaan.base.utils.executionlocker

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Extension function to enable execution with locking state
 */
fun withExecutionLocker(delay: Long = 2000, executionBody: () -> Unit) {
    if (!ExecutionLocker.isLocked) {
        executionBody.invoke()
        ExecutionLocker.lockExecution(delay)
    }
}

suspend fun withExecutionLockerSuspended(
    delay: Long = 2000, executionBody: suspend CoroutineScope.() -> Unit
) {
    withContext(Dispatchers.Main) {
        if (!ExecutionLocker.isLocked) {
            executionBody.invoke(this)
            ExecutionLocker.lockExecution(delay)
        }
    }
}