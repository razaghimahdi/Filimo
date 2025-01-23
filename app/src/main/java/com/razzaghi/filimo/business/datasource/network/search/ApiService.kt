package com.razzaghi.filimo.business.datasource.network.search

import com.razzaghi.filimo.business.datasource.network.search.responses.SearchDTO
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path

interface ApiService {

    companion object {
        private const val SEARCH = "movie/movie/list/tagid/1000300/text/{Query}/sug/on"
    }

    @GET(SEARCH)
    suspend fun search(
        @HeaderMap headers: HashMap<String, String> = hashMapOf("jsonType" to "simple"),
        @Path("Query") query: String
    ): SearchDTO


}