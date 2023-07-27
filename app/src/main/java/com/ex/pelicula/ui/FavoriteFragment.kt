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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.pelicula.db.AppDatabase
import com.ex.pelicula.R
import com.ex.pelicula.adapter.AdapterFavorite
import com.ex.pelicula.databinding.FragmentFavBinding
import com.ex.pelicula.viewModel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    private lateinit var adapter: AdapterFavorite
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


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

// lifecycleScope.launch(Dispatchers.Main) -> Asenkron işlemleri başlatmak için kullanılır.
// Uzun süren işlemleri ana iş parçacığından ayırarak ve arka plan iş parçacığında çalıştırarak, uygulamanızın daha düzgün ve daha hızlı çalışmasını sağlayabilirsiniz.


        var userId = viewModel.getUser()


        // Dispatchers.IO iken çalıştı ama sonradan hata verdi Main yapınca çalıştı ?????
        lifecycleScope.launch(Dispatchers.Main) {

            var movieList = viewModel.getFavorite(userId)


            adapter = AdapterFavorite(movieList) {

                var fragment = DetailFragment()
                var bundle = Bundle()
                bundle.putString("id", it.id!!.toLong().toString())
                bundle.putString("name", it.original_title)
                bundle.putString("vote_average", it.vote_average)
                bundle.putString("overview", it.overview)
                bundle.putString("imageURL", it.backdrop_path)
                bundle.putBoolean("isFavorite", true)
                fragment.arguments = bundle


                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.constraint, fragment)
                    .addToBackStack(null)
                    .commit()
            }

            binding.recyclerview.adapter = adapter
            binding.recyclerview.layoutManager = LinearLayoutManager(context)

        }
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
            }
        })

        return binding.root
    }
}

