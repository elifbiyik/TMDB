package com.ex.pelicula.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentFavBinding
import com.ex.pelicula.ui.FavoriteFragment


class AdapterFavorite(var list: ArrayList<String> = ArrayList()) :
    RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {

  //  var list: ArrayList<String> = ArrayList()

    class ViewHolder(var binding: FragmentFavBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        var binding = DataBindingUtil.inflate<FragmentFavBinding>(
            inflater,
            R.layout.fragment_fav,
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

    }
}








