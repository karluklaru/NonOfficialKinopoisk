package com.bignerdranch.android.tinkofffintech.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.tinkofffintech.R
import com.bignerdranch.android.tinkofffintech.api.main.MainRepository
import com.bignerdranch.android.tinkofffintech.api.main.MainViewModel
import com.bignerdranch.android.tinkofffintech.api.main.MainViewModelFactory
import com.bignerdranch.android.tinkofffintech.api.pojo.Movie
import com.bignerdranch.android.tinkofffintech.api.retrofit.RetrofitService
import com.bignerdranch.android.tinkofffintech.databinding.ActivityMainBinding
import com.bignerdranch.android.tinkofffintech.databinding.FragmentMovieListBinding
import okhttp3.OkHttpClient

class MovieListFragment : Fragment() {
    lateinit var viewModel: MainViewModel
    private val movieAdapter = MovieAdapter(this::onMovieClick, this::onMovieLongClick)
    private lateinit var binding: FragmentMovieListBinding

    private fun onMovieClick(movie: Movie) {
        val filmInfoIntent = MovieInfoActivity.createIntent(requireActivity(), movie.filmId)
        startActivity(filmInfoIntent)
    }

    private fun onMovieLongClick(movie: Movie) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val okHttpClient = OkHttpClient()
        val retrofitService = RetrofitService.getInstance("https://kinopoiskapiunofficial.tech", okHttpClient)
        val mainRepository = MainRepository(retrofitService)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
        }

        viewModel = ViewModelProvider(this, MainViewModelFactory(mainRepository)).get(MainViewModel::class.java)

        viewModel.topMovies.observe(viewLifecycleOwner) {
            movieAdapter.setMovies(it)
        }

        viewModel.getTopMovies()
    }

    companion object {
        fun newInstance() : MovieListFragment {
            return MovieListFragment()
        }
    }
}