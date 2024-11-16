package com.mubin.gozayaan.ui.home

import androidx.lifecycle.ViewModel
import com.mubin.gozayaan.data.model.DestinationResponse
import com.mubin.gozayaan.domain.usecases.GetDestinationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetDestinationsUseCase) : ViewModel() {

    val uiState by lazy { HomeUiState() }

    suspend fun getDestinations() : DestinationResponse? {

        return withContext(Dispatchers.IO) {
            useCase.run("")
        }

    }

}