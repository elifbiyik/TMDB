package com.ex.pelicula.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ex.pelicula.repository.RepositoryFavorite


class DetailViewModel() : ViewModel() {

    private var repo: RepositoryFavorite = RepositoryFavorite()
 //   var favMutableLiveData = MutableLiveData<List<String>>()

    var favMutableLiveData = MutableLiveData<List<String>>()

    fun addFavorite(movieName: String) {

        var list = repo.addFavorite(movieName)
        favMutableLiveData.value = list

        Log.d("xx", favMutableLiveData.value.toString())
        Log.d("xx", list.toString())


    }

    fun removeFavorite(movieName: String) {

        var list = repo.removeFavorite(movieName)
        favMutableLiveData.value = list

    }
}