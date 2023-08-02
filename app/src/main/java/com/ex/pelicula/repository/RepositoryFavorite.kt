package com.ex.pelicula.repository

import android.util.Log
import com.ex.pelicula.db.FavoriteDao
import com.ex.pelicula.models.Movie
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryFavorite @Inject constructor(private val favoriteDao: FavoriteDao , private val repoUser: RepositoryUser
) {




    //  private var list: ArrayList<Movie> = ArrayList()

   suspend fun addFavorite(movie: Movie) {
            favoriteDao.insert(movie)
    }

    suspend fun removeFavorite(movie: Movie) {
            favoriteDao.delete(movie)
    }

    suspend fun getFavorite(userId : String): List<Movie> {
        Log.d("ROOMRepositoryFav", repoUser.getUser().toString())
            return favoriteDao.getAll(userId)

   //    return favoriteDao.getAll()
    }

    fun getUser(): String {
        return repoUser.currentUser()
    }

    fun getUserEmail(): String {
        return repoUser.currentUserEmail()
    }




/*    suspend fun insertFav (movie : Movie) {
        favoriteDao.insert(movie)
    }

    fun getAllFavMovie() {
        favoriteDao.getAll()
    }*/
}


/*    companion object { ......

     - sınıfın bir singleton nesnesini oluşturur.
     - Bu sayede aynı liste ile işlem yapılır. ( Diğer türlü sürekli yeni liste oluşturup ekleme yapıyordu ( yani listeye 1'den fazla eklenmiyordu 2. film için yeni liste yapıyordu. ))





 */
