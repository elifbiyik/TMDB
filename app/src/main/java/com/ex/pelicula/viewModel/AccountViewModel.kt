package com.ex.pelicula.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ex.pelicula.models.FavoriteMovies
import com.ex.pelicula.repository.RepositoryFavorite
import com.ex.pelicula.repository.RepositoryUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import javax.inject.Inject


@HiltViewModel
class AccountViewModel @Inject constructor(private val repo: RepositoryUser) : ViewModel() {


    var userMutableLiveData = MutableLiveData<List<String>>()



    fun getUser ()  {

        var user = repo.getUser()
        userMutableLiveData.value = user

        Log.d("userIdUser", user.toString())


    }



}