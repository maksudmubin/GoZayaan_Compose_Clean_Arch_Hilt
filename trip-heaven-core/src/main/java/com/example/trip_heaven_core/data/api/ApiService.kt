package com.example.trip_heaven_core.data.api

import com.example.trip_heaven_core.data.model.DestinationResponse
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Implementation of the [ApiEndpoints] interface that provides network operations.
 * This class uses Retrofit to delegate API calls defined in [ApiEndpoints].
 *
 * @param retrofit An instance of Retrofit injected by Dagger Hilt.
 */
internal class ApiService @Inject constructor(retrofit: Retrofit) : ApiEndpoints {

    // Lazily initialize the ApiEndpoints implementation using Retrofit
    private val api by lazy { retrofit.create(ApiEndpoints::class.java) }

    /**
     * Fetches a list of destinations from the server.
     * Delegates the call to the [ApiEndpoints.getDestinations] implementation.
     *
     * @return A [Call] object that encapsulates the response [DestinationResponse] or `null`.
     */
    override fun getDestinations(): Call<DestinationResponse?> = api.getDestinations()

}