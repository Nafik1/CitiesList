package org.example.citieslist.domain.usecases

import org.example.citieslist.di.kodein
import org.example.citieslist.domain.repository.SearchRepository
import org.kodein.di.erased.instance

class SearchCityUseCase {
    private val searchRepository : SearchRepository by kodein.instance()

    suspend operator fun invoke(query: String) = searchRepository.search(query)
}