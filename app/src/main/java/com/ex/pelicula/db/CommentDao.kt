package com.ex.pelicula.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ex.pelicula.models.Comment


@Dao
interface CommentDao {


    @Insert
    suspend fun insert (comment: Comment)


    @Delete
    suspend fun delete (list: List<Comment>)



    @Query("SELECT *FROM Comment Where movieId = :movieId AND userId = :userId")
    suspend fun getCommentAndRating (movieId : Long, userId : String) : List<Comment>



    @Query("UPDATE Comment SET comment = :comment, point = :point, userEmail = :userEmail  Where movieId = :movieId AND userId = :userId")
    fun updateComment(userId: String, movieId: Long, comment: String, point : Float, userEmail: String)



    @Query("SELECT *FROM Comment Where movieId = :movieId")
    suspend fun getAll (movieId : Long) : List<Comment>



    // https://bugrayetkinn.medium.com/android-kotlin-coroutines-d9ea2f004ce8

}



