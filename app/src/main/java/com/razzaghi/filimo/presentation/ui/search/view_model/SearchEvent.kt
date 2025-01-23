package com.razzaghi.filimo.presentation.ui.search.view_model

import com.razzaghi.filimo.business.core.NetworkState
import com.razzaghi.filimo.business.core.ViewEvent

sealed class SearchEvent : ViewEvent {

    data class OnUpdateQuery(val query: String) : SearchEvent()

    data object Search : SearchEvent()

    data object OnRetryNetwork : SearchEvent()

    data class OnUpdateNetworkState(val networkState: NetworkState) : SearchEvent()
}
