package com.ex.pelicula.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Movie")
data class Movie(

    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Long,
    var original_language: String?,

    @ColumnInfo(name = "backdrop_path")
    var backdrop_path: String?,

    @ColumnInfo(name = "original_title")
    var original_title: String,

    @ColumnInfo(name = "overview")
    var overview: String?,
    var popularity: Float?,

    @ColumnInfo(name = "poster_path")
    var poster_path: String,

    var release_date: String?,
    var title: String?,
    var video: Boolean?,

    @ColumnInfo(name = "vote_average")
    var vote_average: String?,
    var vote_count: Int?,
)