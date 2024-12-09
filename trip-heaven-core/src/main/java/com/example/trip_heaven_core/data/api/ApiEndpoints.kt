package com.example.trip_heaven_core.data.api

import com.example.trip_heaven_core.data.model.DestinationResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 * Interface defining API endpoints for network calls.
 * This interface is used with Retrofit to define HTTP methods and their respective paths.
 */
internal interface ApiEndpoints {

    /**
     * Fetches a list of destinations from the server.
     * The base URL itself is targeted by using "." in the @GET annotation.
     *
     * @return A [Call] object that encapsulates the response [DestinationResponse] or `null`.
     */
    @GET(".") // "." indicates the base URL itself
    fun getDestinations() : Call<DestinationResponse?>

}