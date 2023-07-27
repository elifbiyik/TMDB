package com.ex.pelicula.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ex.pelicula.models.Comment


@Dao
interface CommentDao {


    @Insert
    suspend fun insert (comment: Comment)


    @Delete
    suspend fun delete (comment: Comment)

    @Query("SELECT *FROM Comment Where movieId = :movieId")
    suspend fun getAll (movieId : Long) : List<Comment>

}



