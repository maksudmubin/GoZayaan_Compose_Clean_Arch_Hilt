package com.example.trip_heaven_core.domain.usecases

import com.example.core.utils.logger.GzLogger
import com.example.core.utils.usecase.UseCase
import com.example.trip_heaven_core.data.model.DestinationResponse
import com.example.trip_heaven_core.domain.repo.AppRepository
import javax.inject.Inject

/**
 * Use case responsible for fetching the list of destinations.
 * This use case interacts with the [AppRepository] to retrieve data and encapsulate the business logic.
 * It inherits from [UseCase] to provide a standard structure for use cases in the application.
 *
 * @param repository The [AppRepository] instance used to retrieve destination data.
 */
interface GetDestinationsUseCase {
    suspend fun run(params: String): DestinationResponse?
}

internal class GetDestinationsUseCaseImpl @Inject constructor(
    private val repository: AppRepository,
) : GetDestinationsUseCase {

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