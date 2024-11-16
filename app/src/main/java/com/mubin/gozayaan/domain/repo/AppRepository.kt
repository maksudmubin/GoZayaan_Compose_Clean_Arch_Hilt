package com.mubin.gozayaan.domain.repo

import com.mubin.gozayaan.data.model.DestinationResponse

interface AppRepository {

    suspend fun getDestinations() : DestinationResponse?

}