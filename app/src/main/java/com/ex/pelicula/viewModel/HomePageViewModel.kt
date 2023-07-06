package com.ex.pelicula.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.pelicula.models.GetMoviesResponse
import com.ex.pelicula.models.Movie
import com.ex.pelicula.repository.RepositoryMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val repo: RepositoryMovie) : ViewModel() {

//   var movieMutableList: MutableLiveData<List<GetMoviesResponse>> = MutableLiveData() yapmıyoruz
//   ÇÜNKÜ: Movie bilgilerim tutulsun istiyorum. Response.body içinde-> page,results... bilgilerinin içeriyor.
//   İstediğim şey results içindeki movielere ulaşmak bu yüzden movie türünde tutmak gerekiyor.

// EĞER GetMoviesResponse kullanırsam -> TYPE MISMATCH hatası verir.
// responseBody.results List<Movie> türünde ama mutableLiveData'ya bunu eklemem lazım. bu yüzden GetMoviesResponse yerine Movie kullan.


    var movieMutableList: MutableLiveData<List<Movie>> = MutableLiveData()


    fun getMoviePopular(page: Int) {

        repo.getDataPopular(page).enqueue(object : Callback<GetMoviesResponse> {
            override fun onResponse(
                call: Call<GetMoviesResponse>,
                response: Response<GetMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()                  //responseBody : GetMoviesResponse

                    if (responseBody != null) {
                        Log.d("Repository1","${responseBody}")             // GetMoviesResponse(page=1, results=[Movie(id=385687, original_language=en,
                        Log.d("Repository2","${responseBody.results}")    //[Movie(id=385687, original_language=en


         //               var x = responseBody.results.sortedBy {it.vote_average}   //-> Küçükten büyüğe sıraladı

                        movieMutableList.value = responseBody.results.sortedByDescending { it.vote_average }
                        Log.d("Repository3", movieMutableList.value.toString())


                    } else {
                        Log.d("RepositoryLatest4", "Null")
                    }
                }
            }
            override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                Log.d("RepositoryLatest5", "${t.message}")
            }

        })
    }




    /*
        fun getMovie(page: Int) {
            repo.getData(page).enqueue(object : Callback<List<GetMoviesResponse>> {
                override fun onResponse(
                    call: Call<List<GetMoviesResponse>>,
                    response: Response<List<GetMoviesResponse>>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            movieMutableList.value = responseBody!!
                            Log.d("Repository", responseBody.toString())
                        } else {
                            Log.d("Repository", "else")
                        }
                    }
                }
                override fun onFailure(call: Call<List<GetMoviesResponse>>, t: Throwable) {
                    Log.d("Repository", "${t.message}")
                }
            })
        }
        // " Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path $ " hatası veriyor.
    */


}
