package com.ex.pelicula.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ex.pelicula.models.Movie

@Dao
interface FavoriteDao {

    @Insert
    suspend fun insert (favorite : Movie)

    @Delete
    suspend fun delete (favorite : Movie)


    @Query ("SELECT *FROM Favorite WHERE userId = :userId")
    suspend fun getAll (userId: String) : List<Movie>







}




