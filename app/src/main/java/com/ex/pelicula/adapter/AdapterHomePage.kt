package com.ex.pelicula.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ex.pelicula.databinding.FragmentHomePageItemBinding
import com.ex.pelicula.models.Movie

class AdapterHomePage(
    var movieList: List<Movie>,
    private val onClick: (Movie) -> Unit       //Movie tipinde parametre alır ve bir şey döndürmez
) : RecyclerView.Adapter<AdapterHomePage.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentHomePageItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        var movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(private val binding: FragmentHomePageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {

            with(binding) {
                binding.movie = movie
                root.setOnClickListener { onClick(movie) }
                executePendingBindings()
            }
        }
    }
}

