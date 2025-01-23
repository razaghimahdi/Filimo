package com.razzaghi.filimo.presentation.ui.search.view_model

import com.razzaghi.filimo.business.core.NetworkState
import com.razzaghi.filimo.business.core.ProgressBarState
import com.razzaghi.filimo.business.core.ViewState
import com.razzaghi.filimo.business.domain.search.Search


data class SearchState(
    val query:String = "",
    val search: Search = Search(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val networkState: NetworkState = NetworkState.Good,
) : ViewState
