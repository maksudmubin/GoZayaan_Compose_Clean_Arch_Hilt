package com.example.core.utils.usecase

import com.example.core.utils.logger.GzLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A base class for all use cases in the application.
 * Use cases represent individual business logic actions, and this class provides a consistent
 * structure for implementing them, including the ability to run on a background thread.
 *
 * @param Type The type of the result returned by the use case.
 * @param Params The type of the parameters required to execute the use case.
 */
abstract class UseCase<Type, in Params> where Type : Any {

    /**
     * Executes the use case with the provided parameters.
     *
     * @param params The parameters required to execute the use case.
     * @return The result of the use case, or `null` if an exception occurs.
     */
    abstract suspend fun run(params: Params): Type?

    /**
     * Wraps the `run` method to execute it on the IO dispatcher for background execution.
     *
     * @param params The parameters required to execute the use case.
     * @return The result of the use case, or `null` if an exception occurs.
     */
    private suspend fun getResponse(params: Params): Type? = withContext(Dispatchers.IO) {
        GzLogger.d("UseCase", "Executing use case with params: $params")
        run(params).also { result ->
            GzLogger.d("UseCase", "Execution result: $result")
        }
    }

    /**
     * Operator function to invoke the use case with the provided parameters.
     *
     * @param params The parameters required to execute the use case.
     * @return The result of the use case, or `null` if an exception occurs.
     */
    suspend operator fun invoke(params: Params): Type? = getResponse(params)

    /**
     * A placeholder class to represent use cases that do not require any parameters.
     */
    class None
}