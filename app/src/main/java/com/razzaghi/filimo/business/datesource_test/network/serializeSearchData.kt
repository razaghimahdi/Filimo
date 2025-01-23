package com.razzaghi.filimo.business.datesource_test.network

import com.razzaghi.filimo.business.datasource.network.search.responses.SearchDTO
import com.razzaghi.filimo.business.datasource.network.search.responses.toSearch
import com.razzaghi.filimo.business.domain.search.Search
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


fun serializeSearchData(jsonData: String): Search {
    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    val jsonAdapter: JsonAdapter<SearchDTO> = moshi.adapter(SearchDTO::class.java)
    val result = jsonAdapter.fromJson(jsonData)
    return result!!.toSearch()
}
