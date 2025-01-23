package com.razzaghi.filimo.business.datasource.network.search.responses

import com.razzaghi.filimo.business.domain.search.Category
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryDTO(
    @Json(name = "title") val title: String?,
    @Json(name = "titleEn") val titleEn: String?
)

fun CategoryDTO.toCategory() = Category(
    title = title ?: "",
    titleEn = titleEn ?: "",
)