package com.bignerdranch.android.tinkofffintech.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.tinkofffintech.api.pojo.Movie
import com.bignerdranch.android.tinkofffintech.databinding.FragmentMovieListBinding
import com.bignerdranch.android.tinkofffintech.ui.utils.debounce

class MovieListFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private val movieAdapter = MovieAdapter(this::onMovieClick, this::onMovieLongClick)
    private lateinit var binding: FragmentMovieListBinding

    val onSearchQueryReceive: (String) -> Unit = debounce(
        300L,
        lifecycleScope
    ) { viewModel.searchMovies(it) }

    private fun onMovieClick(movie: Movie) {
        val filmInfoIntent = MovieInfoActivity.createIntent(requireActivity(), movie.filmId)
        startActivity(filmInfoIntent)
    }

    private fun onMovieLongClick(movie: Movie) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        viewModel = ViewModelProvider(this, MainViewModelFactory())[MainViewModel::class.java]

        viewModel.topMovies.observe(viewLifecycleOwner) {
            movieAdapter.setMovies(it)
        }
        viewModel.searchedMovies.observe(viewLifecycleOwner) { (query, movies) ->
            if (movies.isEmpty() && query.isEmpty()) {
                switchFields(clearSearchField = false)
                resetMovieSearch()
            } else if (movies.isEmpty() && query.isNotEmpty()) {
                binding.labelNotFound.isVisible = true
                movieAdapter.setMovies(emptyList())
            } else {
                movieAdapter.setMovies(movies)
                binding.labelNotFound.isGone = true
            }
        }

        viewModel.getTopMovies()
    }

    private fun resetMovieSearch() {
        movieAdapter.setMovies(viewModel.topMovies.value ?: emptyList())
        binding.labelNotFound.isGone = true
    }

    private fun setupUi() = with(binding) {
        searchField.doOnTextChanged { text, start, before, count -> onSearchQueryReceive(text.toString()) }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
        }
        searchBtn.setOnClickListener {
            switchFields(searchField.text.isNotEmpty())
        }
    }

    private fun switchFields(clearSearchField: Boolean = true) = with(binding) {
        searchField.isVisible = !searchField.isVisible
        if (searchField.isGone) {
            if (clearSearchField)
                searchField.setText("")
            resetMovieSearch()
        }
        title.isVisible = !title.isVisible
    }

    companion object {
        fun newInstance(): MovieListFragment {
            return MovieListFragment()
        }
    }
}