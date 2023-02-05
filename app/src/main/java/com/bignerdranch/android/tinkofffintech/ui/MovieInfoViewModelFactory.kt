package com.bignerdranch.android.tinkofffintech.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.tinkofffintech.api.movieinfo.MovieInfoRepository

class MovieInfoViewModelFactory constructor(private val repository: MovieInfoRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom((MovieInfoViewModel::class.java))) {
            MovieInfoViewModel(this.repository) as T
        } else {
            throw java.lang.IllegalArgumentException("ViewModel Not Found")
        }
    }
}