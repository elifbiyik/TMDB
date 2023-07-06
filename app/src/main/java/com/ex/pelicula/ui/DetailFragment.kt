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
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentDetailBinding
import com.ex.pelicula.viewModel.DetailViewModel


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()



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



        //     var movieName = arguments?.getSerializable("name").toString()

        var movieName = "xx"
        binding.movieName.text = movieName   // ÇALIŞTI ??  // XML'de @{movie... yapınca tv'de xx yazmıyor. XML'de o kısmı silince binding kullanılmıyor.
        Log.d("xx", movieName)
        // Adapterdan bilgi gelme işlemleri


        binding.detailfavorite.setOnClickListener {

/*
            var color1 = ContextCompat.getColor(requireContext(), R.color.Red)
            when (color1){
                ContextCompat.getColor(requireContext(), R.color.Red) -> viewModel.removeFavorite(movieName)
                ContextCompat.getColor(requireContext(), R.color.black) -> viewModel.addFavorite(movieName)
            }
*/

            //R.color sınıfı kullanılmıyor. ContextCompat kullanmak gerek


            var click = true

            if(click == true ) {
                var color = ContextCompat.getColor(requireContext(), R.color.Red)
                binding.detailfavorite.setColorFilter(color)
                viewModel.addFavorite(movieName)             // [xx] dönüyor.
            }

            else {
                var color = ContextCompat.getColor(requireContext(), R.color.black)
                binding.detailfavorite.setColorFilter(color)
                viewModel.removeFavorite(movieName)
            }

            click = false





            /*     if  (color == ContextCompat.getColor(requireContext(), R.color.Red)) {
                     //     else{
                     color = ContextCompat.getColor(requireContext(), R.color.black)
                     binding.detailfavorite.setColorFilter(color)
                     viewModel.removeFavorite(movieName)
                 }*/


            //     eğer kırmızıysa  remove çalışıcak.

        }




        viewModel.favMutableLiveData.observe(viewLifecycleOwner, Observer
        { list ->

            if (!list.isEmpty()) {
                Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()

                // bundle ile buradan göndersem ??

                //            val intent = Intent(context, FavoriteFragment::class.java)
                var fragment = FavoriteFragment()
                var bundle = Bundle()
                bundle.putSerializable("list", list.toString())
                fragment.arguments = bundle
                //             intent.putExtras(bundle)
                //              startActivity(intent)

                Log.d("xxx", list.toString())

                // Diğer sayfayı açtırmya gerek var mı ?
                /*             requireActivity().supportFragmentManager.beginTransaction()
                                 .replace(R.id.constraint, FavoriteFragment()).commit()
             */


            } else Toast.makeText(requireContext(), "Unsuccessful", Toast.LENGTH_SHORT).show()

        })




        return binding.root
    }


}

