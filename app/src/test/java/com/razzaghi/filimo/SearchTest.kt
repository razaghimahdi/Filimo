package com.razzaghi.filimo

import com.razzaghi.filimo.business.core.DataState
import com.razzaghi.filimo.business.core.ProgressBarState
import com.razzaghi.filimo.business.core.UIComponent
import com.razzaghi.filimo.business.datasource.network.search.ApiService
import com.razzaghi.filimo.business.datesource_test.network.SearchServiceFake
import com.razzaghi.filimo.business.datesource_test.network.SearchServiceResponseType
import com.razzaghi.filimo.business.domain.search.Search
import com.razzaghi.filimo.business.usecase.search.SearchUseCase
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchTest {

    private lateinit var searchUseCase: SearchUseCase

    @Test
    fun `search failed with 406 error`() = runBlocking {
        // setup
        val service = SearchServiceFake().build(SearchServiceResponseType.Error406)

        searchUseCase = SearchUseCase(
            service = service
        )

        // Execute the use-case
        val emissions = searchUseCase.execute("Joker").toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<Search>(ProgressBarState.Loading))

        // Confirm second emission is data
        assert(emissions[1] is DataState.Response)
        assert((emissions[1] as DataState.Response).uiComponent is UIComponent.DialogSimple)

        // Confirm loading state is IDLE
        assert(emissions[2] == DataState.Loading<Search>(ProgressBarState.Idle))
    }
    @Test
    fun `search Joker and returns result`() = runBlocking {
        // setup
        val service = SearchServiceFake().build(SearchServiceResponseType.GoodData)

        searchUseCase = SearchUseCase(
            service = service
        )

        // Execute the use-case
        val emissions = searchUseCase.execute("Joker").toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<Search>(ProgressBarState.Loading))

        // Confirm second emission is data
        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data?.searchData?.isNotEmpty() == true)

        // Confirm loading state is IDLE
        assert(emissions[2] == DataState.Loading<Search>(ProgressBarState.Idle))
    }

    @Test
    fun `search Joker and returns nothing`() = runBlocking {
        // setup
        val service = SearchServiceFake().build(SearchServiceResponseType.EmptyList)

        searchUseCase = SearchUseCase(
            service = service
        )

        // Execute the use-case
        val emissions = searchUseCase.execute("Joker").toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<Search>(ProgressBarState.Loading))

        // Confirm second emission is data
        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data?.searchData?.isEmpty() == true)

        // Confirm loading state is IDLE
        assert(emissions[2] == DataState.Loading<Search>(ProgressBarState.Idle))
    }

    @Test
    fun `search Something that is not available from api and returns nothing`() = runBlocking {
        // setup
        val service = SearchServiceFake().build(SearchServiceResponseType.EmptyList)

        searchUseCase = SearchUseCase(
            service = service
        )

        // Execute the use-case
        val emissions = searchUseCase.execute("dsfsegsdv").toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<Search>(ProgressBarState.Loading))

        // Confirm second emission is data
        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data?.searchData?.isEmpty() == true)

        // Confirm loading state is IDLE
        assert(emissions[2] == DataState.Loading<Search>(ProgressBarState.Idle))
    }
}