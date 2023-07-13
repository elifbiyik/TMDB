
package com.ex.pelicula.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentFavItemBinding
import com.ex.pelicula.databinding.FragmentHomePageItemBinding
import com.ex.pelicula.models.FavoriteMovies
import com.ex.pelicula.models.Movie
import java.io.Serializable


class AdapterFavorite(var list: ArrayList<FavoriteMovies>, private val onClick : (FavoriteMovies) -> Unit)  :
    RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {

  //  var list: ArrayList<String> = ArrayList()

    class ViewHolder(var binding: FragmentFavItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

    //    val binding = FragmentFavItemBinding.inflate(inflater,parent,false)


        var binding = DataBindingUtil.inflate<FragmentFavItemBinding>(
            inflater,
            R.layout.fragment_fav_item,
            parent,
            false
        )

        return ViewHolder(binding)

    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var favList =  list[position]
        holder.binding.favMovie = favList

        holder.binding.root.setOnClickListener { onClick(favList) }

    }


}








