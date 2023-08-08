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
// onQueryTextSubmit ->Eğer kelimeyi yazdıktan sonra aramk istiyorsam bu method kullanılır.
// onQueryTextChange -> eğer her harfi yazdığında aramsını istiyorsam bu method kullanılır.

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


//Last Parameter Call -> lambdayı parantez içine koymadan fonksiyon parametresi olarak iletme.
// AdapterHomePage(emptyList(), { ... }) ile aynı anlama geliyor.

     //   movieAdapter = AdapterHomePage(emptyList()) {

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


//        viewModel.getMoviePopular(1)


        viewModel.getMoviePopular().observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                movieAdapter.submitData(lifecycle, it)
            }
        })
        // submitData -> RecyclerView'a bağlar ve yeni veri yüklemesi gerektiğinde otomatik olarak güncelleme yapar.


        /*     viewModel.movieMutableList.observe(viewLifecycleOwner, Observer { movies ->
              movieAdapter.movieList = movies
              movieAdapter.notifyDataSetChanged()
              if (movies.isEmpty()) Toast.makeText(
                  requireContext(),
                  "Unsuccessful",
                  Toast.LENGTH_SHORT
              )
                  .show()
              else Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
          })*/


        // Adapterıma yeni liste oluşturmak yerine olan listeye filtrelenmiş haliyle adaptera gönderdik.
        // ViewModelde Filtrelendi ve MLD'ye atandı
        /*        viewModel.filteredMutableLiveData.observe(viewLifecycleOwner, Observer {
                    movieAdapter.movieList = it
                    movieAdapter.notifyDataSetChanged()
                    if (it.isEmpty()) Toast.makeText(
                        requireContext(),
                        "filteredMutableLiveData Unsuccessful",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    else Toast.makeText(
                        requireContext(),
                        "filteredMutableLiveData Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                })
    */

    }

}

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

