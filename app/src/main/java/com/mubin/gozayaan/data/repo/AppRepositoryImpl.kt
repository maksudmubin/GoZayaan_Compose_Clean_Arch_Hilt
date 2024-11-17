package com.mubin.gozayaan.data.repo

import com.mubin.gozayaan.base.utils.logger.GzLogger
import com.mubin.gozayaan.data.model.DestinationResponse
import com.mubin.gozayaan.data.api.ApiService
import com.mubin.gozayaan.domain.repo.AppRepository
import javax.inject.Inject

/**
 * Implementation of the [AppRepository] interface.
 * This class handles data retrieval logic and delegates network calls to the [ApiService].
 *
 * @param apiService An instance of [ApiService] used to make API calls.
 */
class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AppRepository {

    /**
     * Fetches a list of destinations from the server using the [ApiService].
     *
     * @return A [DestinationResponse] object if the network call is successful, or `null` if an error occurs.
     */
    override suspend fun getDestinations(): DestinationResponse? {
        GzLogger.d("AppRepositoryImpl", "Fetching destinations from repository.")
        val response = try {
            // Make a synchronous network request
            apiService.getDestinations().execute()
        } catch (e: Exception) {
            // Log the exception for debugging purposes
            GzLogger.d("AppRepositoryImpl", "Error during API call: ${e.message}")
            null
        }

        return if (response?.isSuccessful == true) {
            GzLogger.d("AppRepositoryImpl", "API call successful. Parsing response.")
            response.body()
        } else {
            GzLogger.d("AppRepositoryImpl", "API call failed. Response: $response")
            null
        }
    }
}