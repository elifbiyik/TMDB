package com.ex.pelicula.repository

import com.ex.pelicula.api.MovieApiService
import com.ex.pelicula.data.ApiConstant
import com.ex.pelicula.models.GetMoviesResponse
import com.google.android.play.integrity.internal.x
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject


class RepositoryMovie @Inject constructor(private val movieApi: MovieApiService) {

    suspend fun getDataPopular(page: Int): Response<GetMoviesResponse> {
        return movieApi.getPopular(ApiConstant.API_KEY, page)
    }

}


/*
    fun getData(page: Int): Call<List<GetMoviesResponse>> {
        return movieApi.getPopular(ApiConstant.API_KEY, page)
    }

    // İnterface List olmadığı için buda list olmayacak
*/







