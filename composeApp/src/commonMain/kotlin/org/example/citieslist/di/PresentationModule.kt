package org.example.citieslist.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.example.citieslist.domain.usecases.SearchCityUseCase
import org.example.citieslist.presentation.root.RootComponentImpl
import org.example.citieslist.presentation.search.SearchComponentImpl
import org.example.citieslist.presentation.search.SearchStoreFactory
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton

val presentationModule = Kodein.Module("PresentationModule") {

    bind<StoreFactory>() with singleton { provideStoreFactory() }
    bind<SearchCityUseCase>() with singleton { getSearchCityUseCase() }
    bind<SearchStoreFactory>() with singleton { getSearchStoreFactory() }
    bind<SearchComponentImpl.Factory>() with singleton { getSearchComponentImpl() }
    bind<RootComponentImpl.Factory>() with singleton { getRootComponentImpl() }
}

private fun provideStoreFactory(): StoreFactory = DefaultStoreFactory()

private fun getSearchCityUseCase(): SearchCityUseCase = SearchCityUseCase()

private fun getSearchStoreFactory(): SearchStoreFactory = SearchStoreFactory()

private fun getSearchComponentImpl() : SearchComponentImpl.Factory =  SearchComponentImpl.Factory()

private fun getRootComponentImpl() : RootComponentImpl.Factory = RootComponentImpl.Factory()