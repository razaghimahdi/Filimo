package com.razzaghi.filimo.business.datesource_test.network

import com.razzaghi.filimo.business.domain.search.Search
 import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
}

fun serializeSearchData(jsonData: String): Search{
    val data: Search = json.decodeFromString<Search>(jsonData)
    return data
}