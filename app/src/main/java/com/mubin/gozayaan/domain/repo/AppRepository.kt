package com.mubin.gozayaan.domain.repo

import com.mubin.gozayaan.data.model.DestinationResponse

/**
 * Interface representing the repository responsible for data operations related to destinations.
 * This serves as an abstraction layer over data sources like APIs, databases, or caches.
 */
interface AppRepository {

    /**
     * Fetches the list of destinations from the data source (e.g., an API).
     *
     * @return A [DestinationResponse] containing the list of destinations, or `null` if the data fetch fails.
     */
    suspend fun getDestinations() : DestinationResponse?

}
