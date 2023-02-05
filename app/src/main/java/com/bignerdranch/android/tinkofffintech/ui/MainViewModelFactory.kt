package com.bignerdranch.android.tinkofffintech.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.tinkofffintech.api.main.MainRepository
import com.bignerdranch.android.tinkofffintech.api.retrofit.RetrofitService
import okhttp3.OkHttpClient

class MainViewModelFactory : ViewModelProvider.Factory {

    private val okHttpClient = OkHttpClient()
    private val retrofitService =
        RetrofitService.getInstance("https://kinopoiskapiunofficial.tech", okHttpClient)
    private val mainRepository = MainRepository(retrofitService)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom((MainViewModel::class.java))) {
            MainViewModel(mainRepository) as T

        } else {
            throw java.lang.IllegalArgumentException("ViewModel Not Found")
        }
    }
}