package com.mubin.gozayaan.domain.usecases

import com.mubin.gozayaan.base.utils.UseCase
import com.mubin.gozayaan.data.model.DestinationResponse
import com.mubin.gozayaan.domain.repo.AppRepository
import javax.inject.Inject

class GetDestinationsUseCase @Inject constructor(private val repository: AppRepository) : UseCase<DestinationResponse, String>() {
    override suspend fun run(params: String): DestinationResponse? = repository.getDestinations()
}