package com.ex.pelicula.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.pelicula.R
import com.ex.pelicula.adapter.AdapterFavorite
import com.ex.pelicula.databinding.FragmentFavBinding
import com.ex.pelicula.models.FavoriteMovies
import com.ex.pelicula.viewModel.DetailViewModel


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    private lateinit var adapter: AdapterFavorite
    private lateinit var viewModel: DetailViewModel

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

// list null dönüyor
    //         var favList = arguments?.getParcelableArrayList<FavoriteMovies>("list")
// Burda liste dönücek bu listeyi AdapterFavorite vericeksin. !!



       /* val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AdapterFavorite(favList)
        binding.recyclerview.adapter = adapter
*/
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        adapter = AdapterFavorite(viewModel.getFavorite()) {

           var fragment = DetailFragment()
                var bundle = Bundle()
                bundle.putString("name", it.original_title)
                fragment.arguments = bundle


            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.constraint, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)



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
                else Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
            }    })

        return binding.root
    }


}

