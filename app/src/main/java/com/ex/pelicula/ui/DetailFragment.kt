package com.ex.pelicula.ui

import android.annotation.SuppressLint
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
import androidx.lifecycle.lifecycleScope
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentDetailBinding
import com.ex.pelicula.models.FavoriteMovie
import com.ex.pelicula.util.Color
import com.ex.pelicula.util.ImageLoad

import com.ex.pelicula.viewModel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var favMovies: FavoriteMovie

    private lateinit var list: List<FavoriteMovie>


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
        var movieAverage = arguments?.getString("vote_average").toString()
        var id = arguments?.getString("id")!!.toLong()
        var movieOverview = arguments?.getString("overview").toString()
        var movieImage = arguments?.getString("imageURL").toString()  // Backdrop
        var movieImagePoster = arguments?.getString("imagePoster").toString()

        var isFavorite = arguments?.getBoolean("isFavorite")
        // Favori snınıfından detail syfası açıldıpında açılıyro. Fav olup olmadığını görmek için.


        binding.movieName.text = movieName
        binding.movieAverage.text = movieAverage
        binding.movieOverview.text = movieOverview
        binding.movieImage.ImageLoad(movieImage)


        var userId = viewModel.getUser()
        var userEmail = viewModel.getUserEmail()

        lifecycleScope.launch {

            list = viewModel.getFavorite(userId)
            Log.d("ListDetail1", list.toString())

            favMovies = FavoriteMovie(
                id,
                null,
                movieImage,
                movieName,
                movieOverview,
                null,
                movieImagePoster,
                null,
                null,
                null,
                movieAverage,
                null,
                userId
            )


            if (isFavorite == true) {
                binding.detailfavorite.Color(R.color.Red)
            }


            if (list.contains(favMovies)) {
                binding.detailfavorite.Color(R.color.Red)
            }

            binding.detailfavorite.setOnClickListener {

                lifecycleScope.launch {
                    list = viewModel.getFavorite(userId)

                    Log.d("ListDetail2", list.toString())

                    if (list.contains(favMovies)) {
                        binding.detailfavorite.Color(R.color.black)
                        viewModel.removeFavorite(favMovies, userId)
                    } else if (isFavorite == true) {
                        binding.detailfavorite.Color(R.color.black)
                        viewModel.removeFavorite(favMovies, userId)

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.constraint, FavoriteFragment()).addToBackStack(null)
                            .commit()

                    } else {
                        binding.detailfavorite.Color(R.color.Red)
                        viewModel.addFavorite(favMovies, userId)
                    }
                }


/*
                viewModel.favMutableLiveData.observe(viewLifecycleOwner, Observer { list ->

                    if (!list.contains(favMovies)) {
                        binding.detailfavorite.Color(R.color.Red)
                        viewModel.addFavorite(favMovies, userId)
                    }
                    else {
                        binding.detailfavorite.Color(R.color.black)
                        viewModel.removeFavorite(favMovies, userId)
                    }
                })
*/


            }

        }


        binding.comment.setOnClickListener {

            // Comment sayfasına at -> detayına girdiğin bilgiler

            var fragment = CommentFragment()
            var bundle = Bundle()
            bundle.putString("id", id.toString())
            bundle.putString("name", movieName)
            bundle.putString("userId", userId)

            bundle.putString("userEmail", userEmail)

            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.constraint, fragment)
                .addToBackStack(null)
                .commit()
        }



        viewModel.favMutableLiveData.observe(viewLifecycleOwner, Observer { list ->

            if (!list.contains(favMovies)) Toast.makeText(
                requireContext(),
                "Deleted from your favorite list  ",
                Toast.LENGTH_SHORT
            ).show()
            else Toast.makeText(
                requireContext(),
                "Added to your favorite list ",
                Toast.LENGTH_SHORT
            ).show()

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