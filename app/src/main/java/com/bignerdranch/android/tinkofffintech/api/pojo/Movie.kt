package com.bignerdranch.android.tinkofffintech.api.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("filmId")
    val filmId: Int,
    @SerialName("nameRu")
    val nameRu: String? = null,
    @SerialName("posterUrl")
    val posterUrl : String? = null,
    @SerialName("year")
    val year: Int? = null,
    @SerialName("countries")
    val countries: List<Country>? = null,
    @SerialName("genres")
    val genres: List<Genre>? = null
) {

    @Serializable
    data class Country(
        @SerialName("country")
        val country: String? = null
    )
    @Serializable
    data class Genre(
        @SerialName("genre")
        val genre: String? = null
    )
}