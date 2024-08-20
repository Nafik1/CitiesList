package org.example.citieslist.data.repository

import org.example.citieslist.data.mapper.toEntity
import org.example.citieslist.data.network.ApiKtor
import org.example.citieslist.di.kodein
import org.example.citieslist.domain.entity.City
import org.example.citieslist.domain.repository.SearchRepository
import org.kodein.di.erased.instance

class SearchRepositoryImpl: SearchRepository {
    private val apiKtor : ApiKtor by kodein.instance()
    override suspend fun search(query: String): List<City> {
        return apiKtor.getCityFromApi(query).map { it.toEntity() }
    }
}