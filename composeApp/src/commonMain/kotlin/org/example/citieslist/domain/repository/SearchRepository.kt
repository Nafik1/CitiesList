package org.example.citieslist.domain.repository

import org.example.citieslist.domain.entity.City

interface SearchRepository {

    suspend fun search(query: String): List<City>
}