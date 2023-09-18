package com.ex.pelicula.repository

import com.ex.pelicula.api.MovieApiService
import com.ex.pelicula.data.ApiConstant
import com.ex.pelicula.models.GetMoviesResponse
import retrofit2.Response
import javax.inject.Inject


class RepositoryMovie @Inject constructor(private val movieApi: MovieApiService) {

    suspend fun getDataPopular(page: Long): Response<GetMoviesResponse> {
        return movieApi.getPopular(ApiConstant.API_KEY, page)
    }
}