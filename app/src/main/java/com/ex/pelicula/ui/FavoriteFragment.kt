package com.ex.pelicula.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.pelicula.R
import com.ex.pelicula.adapter.AdapterFavorite
import com.ex.pelicula.databinding.FragmentFavBinding
import com.ex.pelicula.models.FavoriteMovies
import com.ex.pelicula.viewModel.DetailViewModel
import com.ex.pelicula.viewModel.HomePageViewModel


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    private lateinit var adapter: AdapterFavorite
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

// list null dönüyor
 //            var favList = arguments?.getSerializable("list")
// Burda liste dönücek bu listeyi AdapterFavorite vericeksin. !!



   /*     val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AdapterFavorite(emptyList())
        binding.recyclerview.adapter = adapter
*/

        adapter = AdapterFavorite(arrayListOf())
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModel.favMutableLiveData.observe(viewLifecycleOwner, Observer { favMovie ->
            if (favMovie != null) {
                adapter.list = favMovie
                adapter.notifyDataSetChanged()

                if (favMovie.isEmpty()) Toast.makeText(
                    requireContext(),
                    "Unsuccessful",
                    Toast.LENGTH_SHORT
                )
                    .show()
                else Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
            }    })

        return binding.root
    }


}

