package com.bignerdranch.android.tinkofffintech.api.pojo

@kotlinx.serialization.Serializable
class MoviesPayload(
    val pagesCount: Int,
    val films: List<Movie>?,
)