package com.mubin.gozayaan.data.api

import com.mubin.gozayaan.data.model.DestinationResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndpoints {

    @GET(".") // "." indicates the base URL itself
    fun getDestinations() : Call<DestinationResponse?>

}