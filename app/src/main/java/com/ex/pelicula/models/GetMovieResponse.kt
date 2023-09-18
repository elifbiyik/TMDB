package com.ex.pelicula.models

data class GetMoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int
)
