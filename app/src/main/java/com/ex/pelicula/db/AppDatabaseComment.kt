package com.ex.pelicula.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ex.pelicula.models.Comment




@Database(entities = [Comment::class], version = 7)
abstract class AppDatabaseComment : RoomDatabase() {
    abstract fun commentDao() : CommentDao
}
