package com.ex.pelicula.models

import com.google.gson.annotations.SerializedName

data class Movie(
    var id: Long,
    var original_language: String,
    var backdrop_path : String,
    var original_title: String,
    var overview: String,
    var popularity: Float,
    var poster_path: String,
    var release_date: String,
    var title: String,
    var video: Boolean,
    var vote_average: String,
    var vote_count: Int
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
