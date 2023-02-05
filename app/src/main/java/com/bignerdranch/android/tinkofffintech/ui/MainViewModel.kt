package com.bignerdranch.android.tinkofffintech.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.tinkofffintech.api.NetworkState
import com.bignerdranch.android.tinkofffintech.api.main.MainRepository
import com.bignerdranch.android.tinkofffintech.api.pojo.Movie
import kotlinx.coroutines.*

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage


    val topMovies = MutableLiveData<List<Movie>>()
    val searchedMovies = MutableLiveData<Pair<String, List<Movie>>>()

    private var job: Job? = null


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getTopMovies() {
        viewModelScope.launch {
            val onePageMovies = ArrayList<Movie>()
            for (i in 1..5) {
                when (val response = repository.getTopMovies(i)) {
                    is NetworkState.Success -> {
                        response.data.films?.let { onePageMovies.addAll(it) }
                        topMovies.postValue(onePageMovies)
                    }
                    is NetworkState.Error -> {
                        if (response.response.code() == 401) {

                        } else {

                        }
                    }
                }
            }
        }
    }

    private fun onError(message: String) {
        _errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun searchMovies(searchQuery: String) {
        searchedMovies.value = searchQuery to if (searchQuery.isNullOrBlank()) {
            emptyList()
        } else {
            topMovies.value!!.filter {
                it.nameRu?.contains(
                    searchQuery,
                    ignoreCase = true
                ) == true
            }
        }
    }
}