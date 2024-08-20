package org.example.citieslist.presentation.search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import org.example.citieslist.di.kodein
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.kodein.di.erased.instance

class SearchComponentImpl(
    private val componentContext: ComponentContext
) : SearchComponent, ComponentContext by componentContext {
    private val searchStoreFactory: SearchStoreFactory by kodein.instance()

    private val store = instanceKeeper.getStore { searchStoreFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchStore.State> = store.stateFlow

    override fun changeSearchQuery(query: String) {
        store.accept(SearchStore.Intent.ChangeSearchQuery(query))
    }

    override fun deleteQuery() {
        store.accept(SearchStore.Intent.DeleteQuery)
    }

     class Factory{
        fun create(
            componentContext: ComponentContext
        ): SearchComponentImpl {
            return SearchComponentImpl(
                componentContext = componentContext
            )
        }
    }
}