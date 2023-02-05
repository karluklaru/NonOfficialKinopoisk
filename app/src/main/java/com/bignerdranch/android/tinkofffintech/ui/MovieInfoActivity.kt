package com.bignerdranch.android.tinkofffintech.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.tinkofffintech.api.main.MainViewModel
import com.bignerdranch.android.tinkofffintech.api.main.MainViewModelFactory
import com.bignerdranch.android.tinkofffintech.api.movieinfo.MovieInfoRepository
import com.bignerdranch.android.tinkofffintech.api.movieinfo.MovieInfoViewModel
import com.bignerdranch.android.tinkofffintech.api.movieinfo.MovieInfoViewModelFactory
import com.bignerdranch.android.tinkofffintech.api.pojo.Movie
import com.bignerdranch.android.tinkofffintech.api.retrofit.RetrofitService
import com.bignerdranch.android.tinkofffintech.databinding.ActivityMovieInfoBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.joinAll
import okhttp3.OkHttpClient

class MovieInfoActivity : AppCompatActivity() {
    lateinit var viewModel: MovieInfoViewModel
    lateinit var binding: ActivityMovieInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val okHttpClient = OkHttpClient()
        val retrofitService = RetrofitService.getInstance("https://kinopoiskapiunofficial.tech", okHttpClient)
        val movieInfoRepository = MovieInfoRepository(retrofitService)
        viewModel = ViewModelProvider(this, MovieInfoViewModelFactory(movieInfoRepository))[MovieInfoViewModel::class.java]

        val filmId = intent.getIntExtra(EXTRA_FILM_ID, 0)

        viewModel.getMovie(filmId)

        viewModel.movie.observe(this) {
            with(binding) {
                Glide.with(this@MovieInfoActivity).load(viewModel.movie.value?.posterUrl).into(poster)
                nameRu.text = viewModel.movie.value?.nameRu
                description.text = viewModel.movie.value?.description
                genres.text = viewModel.movie.value?.genres?.map(Movie.Genre::genre)?.joinToString(", ")
                countries.text = viewModel.movie.value?.countries?.map(Movie.Country::country)?.joinToString(", ")
            }
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val EXTRA_FILM_ID = "movie_id"

        fun createIntent(fragment: FragmentActivity, movieId : Int) : Intent {
            val intent = Intent(fragment, MovieInfoActivity::class.java)
            intent.putExtra(EXTRA_FILM_ID, movieId)
            return intent
        }
    }
}