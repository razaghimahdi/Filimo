package com.razzaghi.filimo.business.usecase.search

import com.razzaghi.filimo.business.core.DataState
import com.razzaghi.filimo.business.core.NetworkState
import com.razzaghi.filimo.business.core.ProgressBarState
import com.razzaghi.filimo.business.datasource.network.search.ApiService
import com.razzaghi.filimo.business.datasource.network.search.responses.toSearch
import com.razzaghi.filimo.business.domain.search.Search
import com.razzaghi.filimo.business.util.handleUseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SearchUseCase(
    private val service: ApiService,
) {


    fun execute(query: String): Flow<DataState<Search>> = flow {

        try {

            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))


            val apiResponse = service.search(query = query)


            val result = apiResponse.toSearch()


            emit(DataState.Data(result))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(handleUseCaseException(e))

        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }


    }


}