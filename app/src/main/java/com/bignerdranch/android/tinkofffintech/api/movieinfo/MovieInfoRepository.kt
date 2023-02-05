package com.bignerdranch.android.tinkofffintech.api.movieinfo

import com.bignerdranch.android.tinkofffintech.api.NetworkState
import com.bignerdranch.android.tinkofffintech.api.retrofit.RetrofitService
import com.bignerdranch.android.tinkofffintech.api.pojo.MovieInfo

class MovieInfoRepository constructor(private val retrofitService: RetrofitService) {
    suspend fun getMovie(id: Int) : NetworkState<MovieInfo> {
        val response = retrofitService.getMovie(id)
        return NetworkState.Success(response)
    }
}