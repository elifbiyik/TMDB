
package com.ex.pelicula.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ex.pelicula.databinding.FragmentFavItemBinding
import com.ex.pelicula.models.FavoriteMovie

class AdapterFavorite(var list: List<FavoriteMovie>,
                      private val onClick: (FavoriteMovie) -> Unit)  :
    RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {


    inner class ViewHolder(var binding: FragmentFavItemBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(favList: FavoriteMovie) {

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
