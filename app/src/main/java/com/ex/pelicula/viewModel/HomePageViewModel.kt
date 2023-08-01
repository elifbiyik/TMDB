package com.ex.pelicula.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
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


/*

Pager -> MoviePagingDataSource içindeki load fonk. çağırır.
Flow -> asenkron veri akışını temsil eder.
        verilerin akış halinde olduğunda bile akoştaki herbir veri parçaını ayrı ayrı ele alınabileceği ve işlenebileceği yapı sağlr.

Flow yerine LiveData'da kullanılır. ViewModel ve View arasındaki iletişim için genelde LiveData kullanılır.


 */

@HiltViewModel
class HomePageViewModel @Inject constructor(private val repo: RepositoryMovie) : ViewModel() {

    // MLD -> düzenlenebiliyor.
    // LD -> sadece okunuyor.

    // LD içinde veri değişirse değişiklikle ilgili bildirim gönderebilir. Bu sayede bu değişikliği algılayıp gerekli işlemler yapılır.
    // MLD içindeki veriler fragment içinde değiştirilebilir.
    // ?? Boş liste ile adapterı başlatıp mld'yi observe ederek doldurmak gibi mi ???


// Paging'te mutableLiveData yerine LiveData kullanılıyor ???

    var movieMutableList1: MutableLiveData<PagingData<Movie>> = MutableLiveData()
    var movieMutableList: MutableLiveData<List<Movie>> = MutableLiveData()
    var filteredMutableLiveData : MutableLiveData<List<Movie>> = MutableLiveData()

// PagingConfig sınıfından nesne
/*

    fun getMoviePopular() : LiveData<PagingData<Movie>> {
       return( Pager(PagingConfig(1)) {
            MoviePagingDataSource(repo)     //Veri kaynağı yönetilit. Veri kaynağı = repo
        }.liveData

        )    }
*/



  // Paging kullandığımız için api dönüşü call olmamalı (Response yaptık). Call olmadığı için callback işlemi yapamadık.

   fun getMoviePopular(page: Int) {

        repo.getDataPopular(page).enqueue(object : Callback<GetMoviesResponse> {
            override fun onResponse(
                call: Call<GetMoviesResponse>,
                response: Response<GetMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()                  //responseBody : GetMoviesResponse

                    if (responseBody != null) {
                        movieMutableList.value = responseBody.results.sortedByDescending { it.vote_average }

                    } else {
                        Log.d("HataVM4", "Null")
                    }
                }
            }

            override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                Log.d("HataVM5onFailure", "${t.message}")
            }

        })
    }


    fun getFilter(search : String) {
      /*  if (search.isBlank()) {
            filteredMutableLiveData.value = movieMutableList.value
        } else {
            filteredMutableLiveData.value = movieMutableList.value?.filter { it.original_title.contains(search, ignoreCase = true) }
        }*/
    }

    // movieMutableList'imdeki verileri alıyor.
    // Verileri filtreliyor.
    // movieMutableLiveData içindeki original_title'ları alır. Bu title'lar search değişkenini içerip içeriyorsa FilteredMLD'ye atar




}


//   var movieMutableList: MutableLiveData<List<GetMoviesResponse>> = MutableLiveData() yapmıyoruz
//   ÇÜNKÜ: Movie bilgilerim tutulsun istiyorum. Response.body içinde-> page,results... bilgilerinin içeriyor.
//   İstediğim şey results içindeki movielere ulaşmak bu yüzden movie türünde tutmak gerekiyor.

// EĞER GetMoviesResponse kullanırsam -> TYPE MISMATCH hatası verir.
// responseBody.results List<Movie> türünde ama mutableLiveData'ya bunu eklemem lazım. bu yüzden GetMoviesResponse yerine Movie kullan.


/*
  Log.d(
                            "VM1",
                            "${responseBody}"
                        )             // GetMoviesResponse(page=1, results=[Movie(id=385687, original_language=en,
                        Log.d(
                            "VM2",
                            "${responseBody.results}"
                        )    //[Movie(id=385687, original_language=en...




*/



