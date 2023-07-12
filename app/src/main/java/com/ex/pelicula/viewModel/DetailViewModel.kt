package com.ex.pelicula.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ex.pelicula.models.FavoriteMovies
import com.ex.pelicula.models.Movie
import com.ex.pelicula.repository.RepositoryFavorite


class DetailViewModel() : ViewModel() {

    private val repo: RepositoryFavorite = RepositoryFavorite.getInstance()
    var favMutableLiveData = MutableLiveData<ArrayList<FavoriteMovies>>()
    var isFav: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun addFavorite(movieList: FavoriteMovies) {
        repo.addFavorite(movieList)
        favMutableLiveData.value = repo.getFavorite()
        isFav.value = true
        Log.d("VM",favMutableLiveData.value.toString())

    }

    fun removeFavorite(movieList: FavoriteMovies) {
        repo.removeFavorite(movieList)
        favMutableLiveData.value = repo.getFavorite()
        isFav.value = false
        Log.d("VM",favMutableLiveData.value.toString())

    }
}