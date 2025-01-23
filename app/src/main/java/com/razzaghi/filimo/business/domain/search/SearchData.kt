package com.razzaghi.filimo.business.domain.search

data class SearchData(
    val id: String,
    val movieId: String,
    val categories: List<Category>,
    val countries: List<Country>,
    val cover: String,
    val descr: String,
    val director: String,
    val duration: Duration,
    val linkKey: String,
    val linkType: String,
    val movieTitle: String,
    val movieTitleEn: String
)