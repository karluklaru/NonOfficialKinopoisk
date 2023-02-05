package com.bignerdranch.android.tinkofffintech.api.main

import com.bignerdranch.android.tinkofffintech.api.NetworkState
import com.bignerdranch.android.tinkofffintech.api.retrofit.RetrofitService
import com.bignerdranch.android.tinkofffintech.api.pojo.MoviesPayload

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getTopMovies(pageNum: Int) : NetworkState<MoviesPayload> {
        val response = retrofitService.getTopMovies(pageNum)
        return NetworkState.Success(response)
    }

}