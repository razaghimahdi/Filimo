package com.razzaghi.filimo.presentation.ui.search.view_model

import androidx.lifecycle.viewModelScope
import com.razzaghi.filimo.business.core.BaseViewModel
import com.razzaghi.filimo.business.core.DataState
import com.razzaghi.filimo.business.core.NetworkState
import com.razzaghi.filimo.business.usecase.search.SearchUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchViewModel(
    private val searchUseCase: SearchUseCase,
) : BaseViewModel<SearchEvent, SearchState, Nothing>() {

    override fun setInitialState() = SearchState()

    override fun onTriggerEvent(event: SearchEvent) {
        when (event) {

            is SearchEvent.OnUpdateQuery -> {
                onUpdateQuery(query = event.query)
            }

            is SearchEvent.Search -> {
                onSearch()
            }

            is SearchEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is SearchEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    private fun onUpdateQuery(query: String) {
        setState { copy(query = query) }
    }


    private fun onSearch() {
        searchUseCase.execute(query = state.value.query).onEach { dataState ->
            when (dataState) {
                is DataState.NetworkStatus -> {
                    onTriggerEvent(SearchEvent.OnUpdateNetworkState(dataState.networkState))
                }

                is DataState.Response -> {
                    setError { dataState.uiComponent }
                }

                is DataState.Data -> {
                    dataState.data?.let {
                        setState { copy(search = it) }
                    }
                }

                is DataState.Loading -> {
                    setState { copy(progressBarState = dataState.progressBarState) }
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun onRetryNetwork() {
        setEvent(SearchEvent.Search)
    }


    private fun onUpdateNetworkState(networkState: NetworkState) {
        setState { copy(networkState = networkState) }
    }


}