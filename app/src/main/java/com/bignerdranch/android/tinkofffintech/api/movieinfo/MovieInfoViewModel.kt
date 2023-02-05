package com.bignerdranch.android.tinkofffintech.api.movieinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.tinkofffintech.api.NetworkState
import com.bignerdranch.android.tinkofffintech.api.pojo.MovieInfo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieInfoViewModel constructor(private val repository: MovieInfoRepository)
    : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
    get() = _errorMessage

    val movie = MutableLiveData<MovieInfo>()

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getMovie(id: Int)  {
        viewModelScope.launch {
            when (val response = repository.getMovie(id)) {
                is NetworkState.Success -> {
                    movie.postValue(response.data)
                }
                is NetworkState.Error -> {
                    if (response.response.code() == 401) {

                    } else {

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

}