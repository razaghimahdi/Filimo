package com.razzaghi.filimo.business.datasource.network.search.responses

import com.razzaghi.filimo.business.domain.search.Search
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchDTO(
    @Json(name = "data") val searchData: List<SearchDataDTO>?
)

fun SearchDTO.toSearch() = Search(
    searchData = searchData?.map { it.toSearchData() }?: emptyList(),
)