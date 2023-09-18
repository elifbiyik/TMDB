package com.ex.pelicula.repository

import com.ex.pelicula.db.DaoFavorite
import com.ex.pelicula.models.FavoriteMovie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryFavorite @Inject constructor(
    private val daoFavorite: DaoFavorite, private val repoUser: RepositoryUser
) {

    suspend fun addFavorite(movie: FavoriteMovie) {
        daoFavorite.insert(movie)
    }

    suspend fun removeFavorite(movie: FavoriteMovie) {
        daoFavorite.delete(movie)
    }

    suspend fun getFavorite(userId: String): List<FavoriteMovie> {
        return daoFavorite.getAll(userId)
    }

    fun getUser(): String {
        return repoUser.currentUser()
    }

    fun getUserEmail(): String {
        return repoUser.currentUserEmail()
    }
}