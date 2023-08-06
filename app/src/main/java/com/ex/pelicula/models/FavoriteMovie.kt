package com.ex.pelicula.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Favorite", primaryKeys = ["id","userId"])
data class FavoriteMovie(

    @ColumnInfo(name = "id")
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

    @ColumnInfo(name = "userId")
    var userId: String

    /*
        // Apide bu özellik yok. Room için bu ekle ??
        @ColumnInfo(name = "userId")
        var userId: String?
        */




)



/*
"id": 385687,
"original_language": "en",
"original_title": "Fast X",
"overview": "Over many missions and against impossible odds, Dom Toretto and his family have outsmarted, out-nerved and outdriven every foe in their path. Now, they confront the most lethal opponent they've ever faced: A terrifying threat emerging from the shadows of the past who's fueled by blood revenge, and who is determined to shatter this family and destroy everything—and everyone—that Dom loves, forever.",
"popularity": 7626.393,
"poster_path": "/fiVW06jE7z9YnO4trhaMEdclSiC.jpg",
"release_date": "2023-05-17",
"title": "Fast X",
"video": false,
"vote_average": 7.3,
"vote_count": 1813
*/
