
package com.ex.pelicula.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentFavItemBinding
import com.ex.pelicula.databinding.FragmentHomePageItemBinding
import com.ex.pelicula.models.Movie

class AdapterFavorite(var list: List<Movie>,
                        private val onClick: (Movie) -> Unit)  :
    RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {


    inner class ViewHolder(var binding: FragmentFavItemBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(favList: Movie) {

            with(binding) {
                favMovie = favList
               root.setOnClickListener {onClick(favList) }
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentFavItemBinding.inflate(inflater,parent,false)


    /*    var binding = DataBindingUtil.inflate<FragmentFavItemBinding>(
            inflater,
            R.layout.fragment_fav_item,
            parent,
            false
        )*/

        return ViewHolder(binding)

    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var favList =  list[position]
        holder.bind(favList)

    }

}
