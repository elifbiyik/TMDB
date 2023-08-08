package com.ex.pelicula.repository

import com.ex.pelicula.db.DaoComment
import com.ex.pelicula.models.Comment
import javax.inject.Inject


class RepositoryComment @Inject constructor(var commentDao: DaoComment){


    suspend fun insert (comment : Comment){
        commentDao.insert(comment)
    }


    suspend fun delete (list : List<Comment>){
        commentDao.delete(list)
    }


   // MovieId -> String? Movie?
    suspend fun getAll(movieId : Long): List<Comment> {
         return commentDao.getAll(movieId)
    }

    suspend fun getCommentAndRating(movieId: Long, userId : String) : List<Comment> {
        return commentDao.getCommentAndRating(movieId, userId)
    }

    fun updateComment(userId: String, movieId: Long, comment: String, point : Float, userEmail : String){
        return commentDao.updateComment(userId, movieId, comment, point, userEmail)
    }

 //   suspend fun update(newComment : Comment){
   //     return commentDao.update(newComment)
 //   }


}
