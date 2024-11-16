package com.mubin.gozayaan.data.api

import com.mubin.gozayaan.data.model.DestinationResponse
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class ApiService @Inject constructor(retrofit: Retrofit) : ApiEndpoints {

    private val api by lazy { retrofit.create(ApiEndpoints::class.java) }

    override fun getDestinations(): Call<DestinationResponse?> = api.getDestinations()


}