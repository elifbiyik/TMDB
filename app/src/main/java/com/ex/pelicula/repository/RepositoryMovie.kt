package com.ex.pelicula.repository

import androidx.paging.PagingSource
import com.ex.pelicula.api.MovieApiService
import com.ex.pelicula.data.ApiConstant
import com.ex.pelicula.db.MovieDao
import com.ex.pelicula.models.FavoriteMovie
import com.ex.pelicula.models.GetMoviesResponse
import com.ex.pelicula.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


class RepositoryMovie @Inject constructor(private val movieApi: MovieApiService, private var movieDao: MovieDao) {

    suspend fun getDataPopular(page: Long): Response<GetMoviesResponse> {
        return movieApi.getPopular(ApiConstant.API_KEY, page)
    }

    suspend fun getDataPopular1(): Response<GetMoviesResponse> {
        return movieApi.getPopular1(ApiConstant.API_KEY, "popularity.desc")

    }


   /* suspend fun getMovie() : PagingSource<Int, Movie> {
        return movieDao.getAll()
    }
*/



    suspend fun getPopular(totalPage : Int): List<Movie> {
        val allMovies = mutableListOf<Movie>()

        for (page in 1 until 10) {
            val response = movieApi.getPopular(ApiConstant.API_KEY, page.toLong())
            if (response.isSuccessful) {
                val movies = response.body()?.results!! ?: emptyList()
                allMovies.addAll(movies.map { item ->
                    Movie(
                        item.id,
                        item.original_language,
                        item.backdrop_path,
                        item.original_title,
                        item.overview,
                        item.popularity,
                        item.poster_path,
                        item.release_date,
                        item.title,
                        item.video,
                        item.vote_average,
                        item.vote_count
                    )
                })
            }
        }


        var x = allMovies.sortedByDescending { it.vote_average }
      movieDao.insertAll(x)
        return x
    }
/*    suspend fun getPopular(): Response<GetMoviesResponse> {

        for (page in 1 until 10) {
            return movieApi.getPopular(ApiConstant.API_KEY, page.toLong())
        }
    }*/
}


/*
    fun getData(page: Int): Call<List<GetMoviesResponse>> {
        return movieApi.getPopular(ApiConstant.API_KEY, page)
    }

    // İnterface List olmadığı için buda list olmayacak
*/







