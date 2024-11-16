package com.mubin.gozayaan.base.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<Type, in Params> where Type : Any {
    abstract suspend fun run(params: Params): Type?

    private suspend fun getResponse(params: Params) = withContext(Dispatchers.IO) { run(params) }

    suspend operator fun invoke(
        params: Params
    ): Type? = getResponse(params)

    class None
}