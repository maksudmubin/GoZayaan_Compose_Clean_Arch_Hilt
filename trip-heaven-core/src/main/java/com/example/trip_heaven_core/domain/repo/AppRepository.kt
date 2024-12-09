package com.example.trip_heaven_core.domain.repo

import com.example.trip_heaven_core.data.model.DestinationResponse

/**
 * Interface representing the repository responsible for data operations related to destinations.
 * This serves as an abstraction layer over data sources like APIs, databases, or caches.
 */
internal interface AppRepository {

    /**
     * Fetches the list of destinations from the data source (e.g., an API).
     *
     * @return A [DestinationResponse] containing the list of destinations, or `null` if the data fetch fails.
     */
    suspend fun getDestinations() : DestinationResponse?

}
