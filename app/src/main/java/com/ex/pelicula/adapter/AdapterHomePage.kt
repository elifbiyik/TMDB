package com.ex.pelicula.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ex.pelicula.databinding.FragmentHomePageItemBinding
import com.ex.pelicula.models.Movie


/*
Normal adapter'dan farklı olarak PagingDataAdapter'ı kalıtım veriyoruz.
2 parametre veriliyor. <Model calss, ViewHolder>

--- DiffCallback --> 2 veri ögesinin eşleştirilmesi için kul.
                    Veri değişikliklerini algılar ve günceller

 */

/*
Adapter'a var movieList: List<Movie> vermedik
ÇÜNKÜ zaten PagingDataAdapter içerisinde verileri yönetecek ve güncellemeleri kontrol edecek otomatik


 */


class AdapterHomePage(private val onClick: (Movie) -> Unit) : PagingDataAdapter<Movie, AdapterHomePage.MovieViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentHomePageItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        var movie = getItem(position)
        movie?.let {
            holder.bind(it)
        }
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

    // RecyclerView'de veri değişikliklerini optimize eder
    object DiffCallback: DiffUtil.ItemCallback<Movie>() {
        // İki öge aynı mı ?
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
        // İki ögenin içeriği aynı mı ??
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}






/*


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

*/

