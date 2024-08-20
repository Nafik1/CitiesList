package org.example.citieslist.di

import org.example.citieslist.data.network.ApiKtor
import org.example.citieslist.data.repository.SearchRepositoryImpl
import org.example.citieslist.domain.repository.SearchRepository
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton

val dataModule = Kodein.Module("NetworkModule") {

    bind<ApiKtor>() with singleton { getApiKtor() }
    bind<SearchRepository>() with singleton { getRepository() }
}

private fun getApiKtor(): ApiKtor = ApiKtor()

private fun getRepository(): SearchRepository = SearchRepositoryImpl()