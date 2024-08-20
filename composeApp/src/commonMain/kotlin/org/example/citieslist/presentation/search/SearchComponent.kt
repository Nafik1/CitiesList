package org.example.citieslist.presentation.search

import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {

    val model: StateFlow<SearchStore.State>

    fun changeSearchQuery(query: String)

    fun deleteQuery()
}