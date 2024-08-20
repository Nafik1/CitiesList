package org.example.citieslist.data.network

import org.example.citieslist.data.model.CityDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class ApiKtor {
    suspend fun getCityFromApi(query: String): List<CityDto> {
        val client = HttpClient {

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.BODY
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = false
                })
            }
        }
        return client.get("${BASE_URL}city?limit=25") {
            headers {
                append("X-Api-Key", "zDn1kW/ZYGPiDHE4RP0hag==FYyfiq3ilM7Ugw7R")
            }
            parameter("name", query)
        }
            .body<List<CityDto>>()
    }


    companion object {

        private const val BASE_URL = "https://api.api-ninjas.com/v1/"
    }
}