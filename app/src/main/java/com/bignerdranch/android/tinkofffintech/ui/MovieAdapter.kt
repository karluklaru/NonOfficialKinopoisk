package com.bignerdranch.android.tinkofffintech.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.tinkofffintech.R
import com.bignerdranch.android.tinkofffintech.api.pojo.Movie
import com.bignerdranch.android.tinkofffintech.databinding.ItemMovieBinding
import com.bumptech.glide.Glide

class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit,
    private val onMovieLongClick: (Movie) -> Unit,
) : RecyclerView.Adapter<MainViewHolder>(){
    var movieList = mutableListOf<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(movies: List<Movie>) {
        this.movieList = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movieList[position]
        val context = holder.itemView.context
        with(holder.binding) {
            root.setOnClickListener {
                onMovieClick(movie)
            }
            root.setOnLongClickListener {
                onMovieLongClick(movie)
                true
            }
            nameRu.text = movie.nameRu
            genre.text = movie.genres?.get(0)?.genre
            year.text = context.getString(R.string.braced_year, movie.year)
            Glide.with(context).load(movie.posterUrl).into(poster)
        }

    }
}

class MainViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

}