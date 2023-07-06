package com.ex.pelicula.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.pelicula.adapter.AdapterHomePage
import com.ex.pelicula.R
import com.ex.pelicula.databinding.ActivityMainBinding
import com.ex.pelicula.viewModel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomePageViewModel by viewModels()
    private lateinit var movieAdapter: AdapterHomePage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner


        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        movieAdapter = AdapterHomePage(emptyList(),requireContext())
        binding.recyclerview.adapter = movieAdapter


      viewModel.getMoviePopular(1)

        // coroutines kullanmadan oldu ?




        viewModel.movieMutableList.observe(viewLifecycleOwner, Observer { list ->


            movieAdapter.movieList = list
            movieAdapter.notifyDataSetChanged()
// MutableLiveDatadaki filmleri adapter'a gönderdik.
// notifyDataSetChanged() önemli ÇÜNKÜ adapterda boş liste var.
// EĞER notifyDataSetChanged kullanmazsam : Boş liste olarak kalıyor. ( Ekranda listeleme olmuyor )
// EĞER notifyDataSetChanged kullanırsam : Boş liste güncelleniyor ve listem ekleniyor. ( Ekranda listeleme oluyor)


            if (list.isEmpty()) Toast.makeText(requireContext(), "Unsuccessful", Toast.LENGTH_SHORT).show()
            else Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }
}


/*
  //          if (movieAdapter.movieList.isEmpty()) binding.progressBar.visibility = View.VISIBLE
  //          else binding.progressBar.visibility = View.GONE

    //        movieAdapter = Adapter(list)

 */


/*

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: com.ex.pelicula._deneme.DenemeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewa = inflater.inflate(R.layout.activity_main, container, false)
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)


        recyclerView = viewa.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dataList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        adapter = com.ex.pelicula._deneme.DenemeAdapter(dataList)
        recyclerView.adapter = adapter

        return viewa
    }
*/