package com.ex.pelicula.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ex.pelicula.models.Movie


@Database(entities = [Movie::class], version = 2)
abstract class AppDatabaseMovie : RoomDatabase() {
    abstract fun movieDao() : DaoMovie
}
