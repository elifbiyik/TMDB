package com.ex.pelicula.repository

import com.ex.pelicula.db.CommentDao
import com.ex.pelicula.models.Comment
import com.ex.pelicula.models.Movie
import javax.inject.Inject


class RepositoryComment @Inject constructor(var commentDao: CommentDao){


    suspend fun insert (comment : Comment){
        commentDao.insert(comment)
    }


    suspend fun delete (comment : Comment){
        commentDao.delete(comment)
    }


   // MovieId -> String? Movie?
    suspend fun getAll(movieId : Long): List<Comment> {
         return commentDao.getAll(movieId)
    }


}
