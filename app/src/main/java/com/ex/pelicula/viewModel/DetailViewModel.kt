package com.ex.pelicula.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.pelicula.models.Movie
import com.ex.pelicula.repository.RepositoryFavorite
import com.ex.pelicula.repository.RepositoryUser
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: RepositoryFavorite) : ViewModel() {


    var favMutableLiveData = MutableLiveData<List<Movie>>()

    fun addFavorite(movie: Movie, userId: String) {
        viewModelScope.launch {
            repo.addFavorite(movie)
            favMutableLiveData.value = repo.getFavorite(userId)

        }
    }

    fun removeFavorite(movieList: Movie, userId: String) {
        viewModelScope.launch {
            repo.removeFavorite(movieList)
            favMutableLiveData.value = repo.getFavorite(userId)

        }
    }

// Geri dönüş değeri olmayan işlemler için viewModelScope.launch kul.
// Veritabanı işlemleri gibi I/O yoğun işlemler için withContext(Dispatchers.IO) kul.


    suspend fun getFavorite(userId: String): List<Movie> {

        Log.d("ROOMVMDetailgetFav", repo.getFavorite(userId).toString())

        return withContext(Dispatchers.IO) {
            repo.getFavorite(userId)
        }
    }


    fun getUser(): String {
        Log.d("ROOM", repo.getUser().toString())
        return repo.getUser()
    }


}