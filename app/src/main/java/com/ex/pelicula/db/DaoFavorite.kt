package com.ex.pelicula.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ex.pelicula.models.FavoriteMovie

@Dao
interface DaoFavorite {

    @Insert
    suspend fun insert (favorite : FavoriteMovie)

    @Delete
    suspend fun delete (favorite : FavoriteMovie)


    @Query ("SELECT *FROM Favorite WHERE userId = :userId")
    suspend fun getAll (userId: String) : List<FavoriteMovie>







}




