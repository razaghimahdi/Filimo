package com.razzaghi.filimo.business.datasource.network.search.responses

import com.razzaghi.filimo.business.domain.search.Country
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDTO(
   @Json(name = "country")  val country: String?,
   @Json(name = "country_en")  val countryEn: String?
)


fun CountryDTO.toCountry() = Country(
   country = country ?: "",
   countryEn = countryEn ?: "",
)