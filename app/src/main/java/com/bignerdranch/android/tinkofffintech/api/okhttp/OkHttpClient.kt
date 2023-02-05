package com.bignerdranch.android.tinkofffintech.api.okhttp

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpClientCreator {

    fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }
}