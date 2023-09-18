package com.ex.pelicula.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.pelicula.R
import com.ex.pelicula.adapter.AdapterHomePage
import com.ex.pelicula.databinding.FragmentHomePageBinding
import com.ex.pelicula.viewModel.HomePageViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private lateinit var viewModel: HomePageViewModel
    private lateinit var movieAdapter: AdapterHomePage
    lateinit var bottomNav: BottomNavigationView

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bottomNav = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNav.visibility = View.VISIBLE

        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let {

                    viewModel.filterData(p0).observe(viewLifecycleOwner, Observer {
                        lifecycleScope.launch {
                            movieAdapter.submitData(lifecycle, it)
                        }
                    })
                }
                return true
            }
        })

        movieAdapter = AdapterHomePage {
            var fragment = DetailFragment()
            var bundle = Bundle()
            bundle.putString("id", it.id.toString())
            bundle.putString("name", it.original_title)
            bundle.putString("vote_average", it.vote_average)
            bundle.putString("overview", it.overview)
            bundle.putString("imageURL", it.backdrop_path) // Detail için Poster'i değil backdrop gönderdik.
            bundle.putString("imagePoster", it.poster_path)  // Favori için poster gönderdik.
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.constraint, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerview.adapter = movieAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        viewModel.getMoviePopular().observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                movieAdapter.submitData(lifecycle, it)
            }
        })
        // submitData -> RecyclerView'a bağlar ve yeni veri yüklemesi gerektiğinde otomatik olarak güncelleme yapar.
    }
}
