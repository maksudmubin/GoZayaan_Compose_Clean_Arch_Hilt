package com.mubin.gozayaan.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * Represents the API response for a list of destinations.
 * This class extends [ArrayList] to directly deserialize JSON arrays into this object
 * using Retrofit and Gson.
 */
class DestinationResponse : ArrayList<DestinationResponse.DestinationResponseItem>() {

    /**
     * Represents a single destination item in the [DestinationResponse].
     * Each property corresponds to a field in the API response.
     */
    @Keep // Ensures the class and its properties are not removed or obfuscated during minification.
    data class DestinationResponseItem(
        @SerializedName("property_name")
        var propertyName: String?, // Name of the property/destination
        @SerializedName("location")
        var location: String?, // Location of the destination
        @SerializedName("rating")
        var rating: Double?, // Rating of the destination, if available
        @SerializedName("description")
        var description: String?, // Brief description of the destination
        @SerializedName("fare")
        var fare: Double?, // Base fare or cost for the destination
        @SerializedName("fare_unit")
        var fareUnit: String?, // Unit of the fare (e.g., per night, per person)
        @SerializedName("is_available")
        var isAvailable: Boolean?, // Indicates availability of the destination
        @SerializedName("hero_image")
        var heroImage: String?, // URL to the hero image of the destination
        @SerializedName("detail_images")
        var detailImages: List<String?>?, // List of URLs to additional images of the destination
        @SerializedName("currency")
        var currency: String? // Currency code for the fare (e.g., USD, EUR)
    )
}