package com.bignerdranch.android.tinkofffintech.api.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieInfo(
    @SerialName("kinopoiskId")
    val filmId: Int,
    @SerialName("nameRu")
    val nameRu: String? = null,
    @SerialName("posterUrl")
    val posterUrl : String? = null,
    @SerialName("year")
    val year: Int? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("countries")
    val countries: List<Movie.Country>? = null,
    @SerialName("genres")
    val genres: List<Movie.Genre>? = null
)