package com.mubin.triphaven.ui.home

import androidx.lifecycle.ViewModel
import com.mubin.triphaven.base.utils.logger.GzLogger
import com.mubin.triphaven.data.model.DestinationResponse
import com.mubin.triphaven.domain.usecases.GetDestinationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel responsible for managing the UI state and business logic for the Home screen.
 * This ViewModel interacts with the use case to fetch destination data and update the UI state.
 *
 * @property useCase An instance of the [GetDestinationsUseCase] injected via Hilt to fetch destination data.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetDestinationsUseCase) : ViewModel() {

    // Lazy initialization of UI state
    val uiState by lazy { HomeUiState() }

    /**
     * Fetches destination data asynchronously by calling the use case in an IO dispatcher.
     *
     * This function runs on a background thread to ensure that network operations or
     * heavy computations do not block the main thread.
     *
     * @return A [DestinationResponse] containing the fetched destinations or null in case of failure.
     */
    suspend fun getDestinations(): DestinationResponse? {
        // Log the action of fetching destinations
        GzLogger.d("HomeViewModel", "Fetching destinations...")

        return withContext(Dispatchers.IO) {
            // Call the use case to fetch the data
            val response = useCase.run("")

            // Log the result of the destination fetching process
            GzLogger.d("HomeViewModel", "Fetched destinations: ${response?.size ?: "null"}")

            response
        }
    }
}