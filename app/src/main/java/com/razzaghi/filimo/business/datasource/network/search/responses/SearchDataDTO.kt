package com.razzaghi.filimo.business.datasource.network.search.responses

import com.razzaghi.filimo.business.domain.search.Duration
import com.razzaghi.filimo.business.domain.search.SearchData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchDataDTO(
    @Json(name = "id") val id: String?,
    @Json(name = "movie_id") val movieId: String?,
    @Json(name = "categories") val categories: List<CategoryDTO>?,
    @Json(name = "countries") val countries: List<CountryDTO>?,
    @Json(name = "cover") val cover: String?,
    @Json(name = "descr") val descr: String?,
    @Json(name = "director") val director: String?,
    @Json(name = "duration") val duration: DurationDTO?,
    @Json(name = "link_key") val linkKey: String?,
    @Json(name = "link_type") val linkType: String?,
    @Json(name = "movie_title") val movieTitle: String?,
    @Json(name = "movie_title_en") val movieTitleEn: String?
)


fun SearchDataDTO.toSearchData() = SearchData(
    id = id ?: "",
    movieId = movieId ?: "",
    cover = cover ?: "",
    descr = descr ?: "",
    director = director ?: "",
    duration = duration?.toDuration() ?: Duration(),
    linkKey = linkKey ?: "",
    linkType = linkType ?: "",
    movieTitle = movieTitle ?: "",
    movieTitleEn = movieTitleEn ?: "",
    categories = categories?.map { it.toCategory() } ?: emptyList(),
    countries = countries?.map { it.toCountry() } ?: emptyList()
)