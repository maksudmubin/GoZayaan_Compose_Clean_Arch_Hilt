package com.mubin.gozayaan.domain.usecases

import com.mubin.gozayaan.base.utils.UseCase
import com.mubin.gozayaan.base.utils.logger.GzLogger
import com.mubin.gozayaan.data.model.DestinationResponse
import com.mubin.gozayaan.domain.repo.AppRepository
import javax.inject.Inject

/**
 * Use case responsible for fetching the list of destinations.
 * This use case interacts with the [AppRepository] to retrieve data and encapsulate the business logic.
 * It inherits from [UseCase] to provide a standard structure for use cases in the application.
 *
 * @param repository The [AppRepository] instance used to retrieve destination data.
 */
class GetDestinationsUseCase @Inject constructor(
    private val repository: AppRepository
) : UseCase<DestinationResponse, String>() {

    /**
     * Executes the use case to fetch the list of destinations.
     * It calls the [AppRepository.getDestinations] method to fetch the data.
     *
     * @param params A string parameter (currently unused in this implementation).
     * @return A [DestinationResponse] containing the list of destinations, or `null` if an error occurs.
     */
    override suspend fun run(params: String): DestinationResponse? {
        GzLogger.d("GetDestinationsUseCase", "Executing GetDestinationsUseCase.")
        return try {
            // Call the repository to fetch destinations
            val destinations = repository.getDestinations()
            GzLogger.d("GetDestinationsUseCase", "Fetched destinations: $destinations")
            destinations
        } catch (e: Exception) {
            // Log any exception that occurs during the fetch
            GzLogger.d("GetDestinationsUseCase", "Error fetching destinations: ${e.message}")
            null
        }
    }
}