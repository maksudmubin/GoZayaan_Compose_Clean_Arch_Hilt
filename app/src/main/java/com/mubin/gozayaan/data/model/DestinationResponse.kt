package com.mubin.gozayaan.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

class DestinationResponse : ArrayList<DestinationResponse.DestinationResponseItem>(){
    @Keep
    data class DestinationResponseItem(
        @SerializedName("property_name")
        var propertyName: String?,
        @SerializedName("location")
        var location: String?,
        @SerializedName("rating")
        var rating: Double?,
        @SerializedName("description")
        var description: String?,
        @SerializedName("fare")
        var fare: Double?,
        @SerializedName("fare_unit")
        var fareUnit: String?,
        @SerializedName("is_available")
        var isAvailable: Boolean?,
        @SerializedName("hero_image")
        var heroImage: String?,
        @SerializedName("detail_images")
        var detailImages: List<String?>?,
        @SerializedName("currency")
        var currency: String?
    )
}