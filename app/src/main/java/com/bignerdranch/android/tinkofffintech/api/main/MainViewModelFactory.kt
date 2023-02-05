package com.bignerdranch.android.tinkofffintech.api.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory constructor(private val repository: MainRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom((MainViewModel::class.java))) {
            MainViewModel(this.repository) as T

        } else {
            throw java.lang.IllegalArgumentException("ViewModel Not Found")
        }
    }
}