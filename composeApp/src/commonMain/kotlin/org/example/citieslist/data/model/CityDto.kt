package org.example.citieslist.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
   @SerialName("name") val name: String,
   @SerialName("country") val country: String,
)
