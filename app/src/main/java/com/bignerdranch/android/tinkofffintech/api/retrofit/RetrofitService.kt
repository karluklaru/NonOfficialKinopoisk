package com.bignerdranch.android.tinkofffintech.api.retrofit

import com.bignerdranch.android.tinkofffintech.api.pojo.MovieInfo
import com.bignerdranch.android.tinkofffintech.api.pojo.MoviesPayload
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopMovies(@Query("page") pageNum: Int) : MoviesPayload

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("/api/v2.2/films/{id}")
    suspend fun getMovie(@Path("id") id: Int) : MovieInfo

    companion object {
        private var retrofitService: RetrofitService? = null

        private val contentType = "application/json".toMediaType()

        @OptIn(ExperimentalSerializationApi::class)
        private val json = Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(contentType)

        fun getInstance(baseUrl: String, okHttpClient: OkHttpClient): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(json)
                    .client(okHttpClient)
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}