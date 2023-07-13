package com.ex.pelicula.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
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
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var adapter: AdapterFavorite
    private lateinit var favMovies: FavoriteMovies


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



        var list = viewModel.getFavorite()
        favMovies = FavoriteMovies(movieName, movieImage)
        Log.d("viewModel.getFavorite()", viewModel.getFavorite().toString())
// Listeye ulaştık.

        binding.detailfavorite.setOnClickListener {

        if (list.contains(favMovies)){
            binding.detailfavorite.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )

            viewModel.removeFavorite(favMovies)
            Log.d(" if (list.contains(favMovies)){", list.toString())

        }
            else {
            binding.detailfavorite.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.Red
                )
            )
           viewModel.addFavorite(favMovies)
            Log.d(" if (list.contains(favMovies))else {{", list.toString())
        }
 }




/*

        viewModel.isFav.observe(viewLifecycleOwner, Observer { value ->
            if (!value) {
                binding.detailfavorite.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                Log.d("isFav", value.toString())
            } else {
                binding.detailfavorite.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Red
                    )
                )
                Log.d("isFav", value.toString())
            }
        })
*/



        viewModel.favMutableLiveData.observe(viewLifecycleOwner, Observer { list ->


                Log.d("viewModel.favMutableLiveData.observe", list.toString())

            if (!list.isEmpty()) {
                Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()

                // bundle ile buradan göndersem ?? Direkt mutableLivedaatya ulaş FavFragten
// bundle ile göndermeye gerek var mı direkt VM'den listeyi Favori fragmentına çekebiliriz ??


              /*  var fragment = FavoriteFragment()
                var bundle = Bundle()
                bundle.putParcelableArrayList("list", list as ArrayList<out Parcelable?>?)
                fragment.arguments = bundle*/



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