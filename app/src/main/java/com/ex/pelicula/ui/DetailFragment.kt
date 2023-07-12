package com.ex.pelicula.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ex.pelicula.R
import com.ex.pelicula.adapter.AdapterFavorite
import com.ex.pelicula.databinding.FragmentDetailBinding
import com.ex.pelicula.models.FavoriteMovies
import com.ex.pelicula.models.GetMoviesResponse
import com.ex.pelicula.models.Movie
import com.ex.pelicula.util.ImageLoadBackground
import com.ex.pelicula.viewModel.DetailViewModel


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private var adapter: AdapterFavorite = AdapterFavorite(arrayListOf())
    private lateinit var favMovie: FavoriteMovies


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner


        var movieName = arguments?.getString("name")!!
        var movieAverage = arguments?.getSerializable("vote_average").toString()
        var movieOverview = arguments?.getSerializable("overview").toString()
        var movieImage = arguments?.getSerializable("imageURL").toString()

        binding.movieName.text = movieName
        binding.movieAverage.text = movieAverage
        binding.movieOverview.text = movieOverview
        binding.movieImage.ImageLoadBackground(movieImage)
        // binding.movieImage.ImageLoad(movieImage)
        // NORMALDE BUNU KULLANICAKSIN AMA URL ÇALIŞMAIDIĞI İÇİN ÜSTTEKİNİ KULLAN !!!!!


        binding.detailfavorite.setOnClickListener {

            val isFav = viewModel.isFav.value ?: false
            if (isFav) {
                binding.detailfavorite.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )

                favMovie = FavoriteMovies(movieName, movieImage)
                viewModel.removeFavorite(favMovie)
            } else {
                binding.detailfavorite.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Red
                    )
                )
                favMovie = FavoriteMovies(movieName, movieImage)
                viewModel.addFavorite(favMovie)
            }


        }





        viewModel.favMutableLiveData.observe(viewLifecycleOwner, Observer { favMovie ->

            if (!favMovie.isEmpty()) {
                Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()

                // bundle ile buradan göndersem ?? Direkt mutableLivedaatya ulaş FavFragten


/*
                var fragment = FavoriteFragment()
                var bundle = Bundle()
                bundle.putSerializable("list", favMovie)
                fragment.arguments = bundle
*/


                Log.d("xxx2", favMovie.toString())


                /*
                                                // Diğer sayfayı açtırmya gerek var mı ?
                                                             requireActivity().supportFragmentManager.beginTransaction()
                                                                 .replace(R.id.constraint, FavoriteFragment()).commit()
                */


            } else Toast.makeText(requireContext(), "Unsuccessful", Toast.LENGTH_SHORT).show()

        })




        return binding.root
    }
}










/*


        favMovie = Movie(original_title = movieName, poster_path = movieImage)

 */





/*
        binding.detailfavorite.setOnClickListener {



            /*     if  (color == ContextCompat.getColor(requireContext(), R.color.Red)) {
                     //     else{
                     color = ContextCompat.getColor(requireContext(), R.color.black)
                     binding.detailfavorite.setColorFilter(color)
                     viewModel.removeFavorite(movieName)
                 }*/
            //     eğer kırmızıysa  remove çalışıcak.

        }
 */