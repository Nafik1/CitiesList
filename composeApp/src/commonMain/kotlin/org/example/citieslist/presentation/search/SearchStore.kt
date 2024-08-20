package org.example.citieslist.presentation.search

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import org.example.citieslist.di.kodein
import org.example.citieslist.domain.entity.City
import org.example.citieslist.domain.usecases.SearchCityUseCase
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.erased.instance

interface SearchStore :
    Store<SearchStore.Intent, SearchStore.State, SearchStore.Label> {

    sealed interface Intent {

        data class ChangeSearchQuery(val query: String) : Intent

        data object DeleteQuery : Intent

    }

    data class State(
        val searchQuery: String,
        val searchState: SearchState

    ) {

        sealed interface SearchState {

            data object Initial : SearchState

            data object Loading : SearchState

            data object Error : SearchState

            data object EmptyResult : SearchState

            data class SuccessLoaded(val cities: List<City>) : SearchState
        }
    }

    sealed interface Label
}

class SearchStoreFactory {
    private val searchCityUseCase: SearchCityUseCase by kodein.instance()
    private val storeFactory: StoreFactory by kodein.instance()
    fun create(): SearchStore =
        object : SearchStore,
            Store<SearchStore.Intent, SearchStore.State, SearchStore.Label> by storeFactory.create(
                name = "SearchStore",
                initialState = SearchStore.State(
                    searchQuery = "",
                    searchState = SearchStore.State.SearchState.Initial
                ),
                bootstrapper = BootstrapperImpl(),
                executorFactory = { ExecutorImpl() },
                reducer = ReducerImpl
            ) {}

    private sealed interface Action


    private sealed interface Msg {

        data class ChangeSearchQuery(val query: String) : Msg

        data object DeleteQuery : Msg

        data object LoadingSearchResult : Msg

        data object SearchResultError : Msg

        data class SearchResultLoaded(val cities: List<City>) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<SearchStore.Intent, Action, SearchStore.State, Msg, SearchStore.Label>() {
        private var searchJob: Job? = null
        override fun executeIntent(intent: SearchStore.Intent, getState: () -> SearchStore.State) {
            when (intent) {
                is SearchStore.Intent.ChangeSearchQuery -> {
                    dispatch(Msg.ChangeSearchQuery(intent.query))
                    searchJob?.cancel()
                    searchJob = scope.launch {
                        delay(300)
                        dispatch(Msg.LoadingSearchResult)
                        try {
                            val cities = searchCityUseCase(getState().searchQuery)
                            dispatch(Msg.SearchResultLoaded(cities = cities))
                        } catch (e: Exception) {
                            if (e is JsonConvertException) {
                                dispatch(Msg.DeleteQuery)
                                print(e.toString())
                            } else {
                                dispatch(Msg.SearchResultError)
                                print(e.toString())
                            }
                        }
                    }
                }

                SearchStore.Intent.DeleteQuery -> {
                    dispatch(Msg.DeleteQuery)
                }
            }
        }

    }

    private object ReducerImpl : Reducer<SearchStore.State, Msg> {
        override fun SearchStore.State.reduce(msg: Msg): SearchStore.State = when (msg) {
            is Msg.ChangeSearchQuery -> {
                copy(searchQuery = msg.query)
            }

            Msg.LoadingSearchResult -> {
                copy(searchState = SearchStore.State.SearchState.Loading)
            }

            Msg.SearchResultError -> {
                copy(searchState = SearchStore.State.SearchState.Error)
            }

            is Msg.SearchResultLoaded -> {
                val searchState = if (msg.cities.isEmpty()) {
                    SearchStore.State.SearchState.EmptyResult
                } else {
                    SearchStore.State.SearchState.SuccessLoaded(cities = msg.cities)
                }
                copy(searchState = searchState)
            }

            Msg.DeleteQuery -> {
                copy(
                    searchQuery = "",
                    searchState = SearchStore.State.SearchState.Initial
                )
            }
        }
    }
}
