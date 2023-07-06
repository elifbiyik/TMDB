package com.ex.pelicula.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ex.pelicula.api.MovieApiService
import com.ex.pelicula.data.ApiConstant
import com.ex.pelicula.models.GetMoviesResponse
import com.ex.pelicula.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class RepositoryMovie @Inject constructor(private val movieApi: MovieApiService) {


/*
    fun getData(page: Int): Call<List<GetMoviesResponse>> {
        return movieApi.getPopular(ApiConstant.API_KEY, page)
    }

    // İnterface List olmadığı için buda list olmayacak
*/

    fun getDataPopular(page: Int): Call<GetMoviesResponse> {
        return movieApi.getPopular(ApiConstant.API_KEY, page)
    }
}




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






