package com.ex.pelicula.repository

import com.ex.pelicula.api.MovieApiService
import com.ex.pelicula.data.ApiConstant
import com.ex.pelicula.models.GetMoviesResponse
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject



class RepositoryMovie @Inject constructor(private val movieApi: MovieApiService) {


    fun getDataPopular(page: Int): Call<GetMoviesResponse> //Response<GetMoviesResponse>
     {
        return movieApi.getPopular(ApiConstant.API_KEY, page)
    }
}







/*
    fun getData(page: Int): Call<List<GetMoviesResponse>> {
        return movieApi.getPopular(ApiConstant.API_KEY, page)
    }

    // İnterface List olmadığı için buda list olmayacak
*/

/* fun getData1(page: Int): Call<List<GetMoviesResponse>> {


            val call = movieApi.getPopular(ApiConstant.API_KEY, page)
            call.enqueue(object : Callback<List<GetMoviesResponse>> {
                override fun onResponse(
                    call: Call<List<GetMoviesResponse>>,
                    response: Response<List<GetMoviesResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body().let {
                            val data = it
                            Log.d("Repository", data.toString())
                        }
                    } else Log.d("Repository", "Else ")
                }

                override fun onFailure(call: Call<List<GetMoviesResponse>>, t: Throwable) {
                    Log.d("Repository", "onFailure")
                }
            })

        }

 }

}*/






