package com.ex.pelicula.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.pelicula.R
import com.ex.pelicula.adapter.AdapterFavorite
import com.ex.pelicula.adapter.AdapterHomePage
import com.ex.pelicula.databinding.ActivityMainBinding
import com.ex.pelicula.databinding.FragmentDetailBinding
import com.ex.pelicula.databinding.FragmentFavBinding
import com.ex.pelicula.viewModel.DetailViewModel


class FavoriteFragment : Fragment() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : AdapterFavorite
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.activity_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

// list null dönüyor
       var favMovie = arguments?.getStringArrayList("list")
        // getSerializable yerine başka bir şey mı kullanılacak ?
// Burda liste dönücek bu listeyi AdapterFavorite vericeksin. !!



        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AdapterFavorite(arrayListOf())
        binding.recyclerview.adapter = adapter



   viewModel.favMutableLiveData.observe(viewLifecycleOwner, Observer { favMovie ->

       adapter.list = favMovie as ArrayList<String>
       adapter.notifyDataSetChanged()

   })





        return binding.root
    }


    }
