package com.ex.pelicula.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ex.pelicula.models.FavoriteMovies

class RepositoryFavorite private constructor() {
    //   kullanıcı değiştiğinde liste sıfırlanması için if kullanılıcak mı ?


    private var list: ArrayList<FavoriteMovies> = ArrayList<FavoriteMovies>()

    fun addFavorite(movieList: FavoriteMovies) {

   //     if (!list.contains(movieList)) {
            list.add(movieList)
            Log.d("Repo", list.toString())
   //     }


    }

    fun removeFavorite(movieList: FavoriteMovies) {
  //      if (list.contains(movieList)) {
            list.remove(movieList)
            Log.d("Repo", list.toString())
   //     }
    }

    fun getFavorite(): ArrayList<FavoriteMovies> {
        return list
    }

    // Liste her seferinde yeniden oluşuyordu ve 2. film eklenmiyordu.
    // Bu kod ve VM'de getInstance ile liste bir kere oluşuyor ve diğer filmler o listeye ekleniyor.

    companion object {
        private var instance: RepositoryFavorite? = null
        fun getInstance(): RepositoryFavorite {
            if (instance == null) {
                instance = RepositoryFavorite()
            }
            return instance as RepositoryFavorite
        }
    }
}
