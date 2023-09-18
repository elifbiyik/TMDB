package com.ex.pelicula.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Comment")
data class Comment(

    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo(name = "userId")
    var userId: String,

    @ColumnInfo(name = "userEmail")
    var userEmail: String,


    @ColumnInfo(name = "movieId")
    var movieId: Long,


    @ColumnInfo(name = "comment")
    var comment: String,

    @ColumnInfo(name = "Point")
    var point: Int
)


