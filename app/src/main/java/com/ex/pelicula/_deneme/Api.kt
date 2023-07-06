package com.ex.pelicula._deneme

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/latest")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "08963bf8ff23326caf0287502c99c505",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}