package com.mubin.gozayaan.data.repo

import com.mubin.gozayaan.data.model.DestinationResponse
import com.mubin.gozayaan.data.api.ApiService
import com.mubin.gozayaan.domain.repo.AppRepository
import javax.inject.Inject

class AppRepositoryImpl
@Inject
constructor(private val apiService: ApiService) : AppRepository {
    override suspend fun getDestinations(): DestinationResponse? {
        val response = try {
            apiService.getDestinations().execute()
        } catch (e: Exception) {
            null
        }

        return if (response?.isSuccessful == true) {
            response.body()
        } else {
            null
        }
    }
}