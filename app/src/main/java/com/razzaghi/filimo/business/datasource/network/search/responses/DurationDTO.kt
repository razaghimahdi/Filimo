package com.razzaghi.filimo.business.datasource.network.search.responses

import com.razzaghi.filimo.business.domain.search.Country
import com.razzaghi.filimo.business.domain.search.Duration
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DurationDTO(
    @Json(name = "text") val text: String?,
    @Json(name = "value") val value: Long?
)

fun DurationDTO.toDuration() = Duration(
    text = text ?: "",
    value = value ?: 0L,
)