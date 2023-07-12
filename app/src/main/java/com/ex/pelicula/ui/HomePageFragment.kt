package com.ex.pelicula.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.ex.pelicula.R
import com.ex.pelicula.adapter.AdapterHomePage
import com.ex.pelicula.databinding.ActivityMainBinding
import com.ex.pelicula.databinding.FragmentHomePageBinding
import com.ex.pelicula.viewModel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private lateinit var viewModel: HomePageViewModel
    private lateinit var movieAdapter: AdapterHomePage

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//Last Parameter Call -> lambdayı parantez içine koymadan fonksiyon parametresi olarak iletme.
// AdapterHomePage(emptyList(), { ... }) ile aynı anlama geliyor.

        movieAdapter = AdapterHomePage(emptyList()) {

            var fragment = DetailFragment()
            var bundle = Bundle()
            bundle.putString("name", it.original_title)
            bundle.putString("vote_average", it.vote_average)
            bundle.putString("overview", it.overview)
            bundle.putString("imageURL", it.backdrop_path) // Poster'i değil backdrop gönderdik.
            fragment.arguments = bundle


            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.constraint, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerview.adapter = movieAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)

        viewModel.movieMutableList.observe(viewLifecycleOwner, Observer { movies ->
            movieAdapter.movieList = movies
            movieAdapter.notifyDataSetChanged()
            if (movies.isEmpty()) Toast.makeText(requireContext(), "Unsuccessful", Toast.LENGTH_SHORT)
                .show()
            else Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
        })
        viewModel.getMoviePopular(1)

    }
}

        /*    binding = DataBindingUtil.inflate(inflater, R.layout.activity_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
     */
        /*  val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

       */
        /*
        movieAdapter = AdapterHomePage(emptyList())
            { movie ->
            var fragment = DetailFragment()
            var bundle = Bundle()
            bundle.putString("name", movie.original_title)
            bundle.putString("vote_average", movie.vote_average)
            bundle.putString("overview", movie.overview)
            fragment.arguments = bundle

            Log.d("hata1", movie.original_title)

            try {


                val transaction = (context as FragmentActivity)
                    .supportFragmentManager.beginTransaction()

                transaction.replace(R.id.constraint, DetailFragment())
                    .addToBackStack(null)
                    .commit()


            } catch (e: Exception) {
                Log.d("hata", e.message.toString())
            }
        }

*/

        /*    movieAdapter = AdapterHomePage(requireContext(),
            emptyList())


        binding.recyclerview.adapter = movieAdapter

        viewModel.getMoviePopular(1)


        viewModel.movieMutableList.observe(viewLifecycleOwner, Observer { list ->

           movieAdapter.movieList = list

            movieAdapter.notifyDataSetChanged()

            if (list.isEmpty()) Toast.makeText(requireContext(), "Unsuccessful", Toast.LENGTH_SHORT)
                .show()
            else Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
        })

        return binding.root

    }



    private fun onClick() {

        val transaction = (context as AppCompatActivity).
           supportFragmentManager.beginTransaction()

        transaction.replace(R.id.constraint, DetailFragment())
            .addToBackStack(null)
            .commit()

    }

     */







/*
// MutableLiveDatadaki filmleri adapter'a gönderdik.
// notifyDataSetChanged() önemli ÇÜNKÜ adapterda boş liste var.
// EĞER notifyDataSetChanged kullanmazsam : Boş liste olarak kalıyor. ( Ekranda listeleme olmuyor )
// EĞER notifyDataSetChanged kullanırsam : Boş liste güncelleniyor ve listem ekleniyor. ( Ekranda listeleme oluyor)

*/

/*
  //          if (movieAdapter.movieList.isEmpty()) binding.progressBar.visibility = View.VISIBLE
  //          else binding.progressBar.visibility = View.GONE

    //        movieAdapter = Adapter(list)

 */

