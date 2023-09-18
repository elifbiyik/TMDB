package com.ex.pelicula.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.ex.pelicula.MoviePagingDataSource
import com.ex.pelicula.models.Movie
import com.ex.pelicula.repository.RepositoryMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(private val repo: RepositoryMovie) : ViewModel() {

    fun getMoviePopular(): LiveData<PagingData<Movie>> {
        return (Pager(
            config = PagingConfig(1),
            pagingSourceFactory = {
                MoviePagingDataSource(
                    repo,
                    ""
                )
            } //Veri kaynağı yönetilir. Veri kaynağı = repo
        )
                ).liveData.cachedIn(viewModelScope)
    }

    fun filterData(search: String): LiveData<PagingData<Movie>> {

        return (Pager(PagingConfig(1)) {
            MoviePagingDataSource(repo, search)
        }.liveData)
    }


    // MLD -> düzenlenebiliyor.
    // LD -> sadece okunuyor.

    // LD içinde veri değişirse değişiklikle ilgili bildirim gönderebilir. Bu sayede bu değişikliği algılayıp gerekli işlemler yapılır.
    // MLD içindeki veriler fragment içinde değiştirilebilir.
    // ?? Boş liste ile adapterı başlatıp mld'yi observe ederek doldurmak gibi mi ???


    /*
    Pager -> MoviePagingDataSource içindeki load fonk. çağırır.
    Flow -> asenkron veri akışını temsil eder.
            verilerin akış halinde olduğunda bile akıştaki herbir veri parçaını ayrı ayrı ele alınabileceği ve işlenebileceği yapı sağlr.

    Flow yerine LiveData'da kullanılır.
    - ViewModel ve View arasındaki iletişim için genelde LiveData kullanılır.
    - Son güncel değeri almak ve Otomatik UI güncellemeleri için LiveData
    - Veri akışını yönetmek Flow
    *** Flow'da işlenen verileri, UI'da göstermek için LiveData kullanılır ***
     */


// Paging'te mutableLiveData yerine LiveData kullanılıyor ???
// PagingConfig sınıfından nesne


    // Pager sınıfı kullanılarak sayfa yapılandırılır.
    // PagingConfig yüklemek için kullanılacak sayfa boyutu. Her sayfada yalnızca 1 öğe yüklenir.
    // Eğer sayfa boyutu düşükse, sayfalamada daha az öğe yüklenir ve kullanıcı daha sık sayfa değiştirmek zorunda kalabilir.
    // Eğer sayfa boyutu yüksekse, sayfalamada daha fazla öğe yüklenir ve kullanıcı daha az sayfa değiştirmek zorunda kalır.

    // .liveData -> LiveDataya dönüştürülür. ( Artık UI katmanını daha kolay yönetrebilirz.)
    // .cachedIn(viewModelScope) -> datayı önbellekte saklar.
    //                              Tekrar dönmek istediğimizde daha hızlı yükler çünkü önbellekte
    //                              Fragment yaşam döngüsü yok olduğunda otomatik yok olur


    // Paging kullandığımız için api dönüşü call olmamalı (Response yaptık). Call olmadığı için callback işlemi yapamadık.
    /*

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
    */
    /*


        fun getFilter(search: String) {
              if (search.isBlank()) {
                  filteredMutableLiveData.value = movieMutableList.value
              } else {
                  filteredMutableLiveData.value = movieMutableList.value?.filter { it.original_title.contains(search, ignoreCase = true) }
              }}
    */

    // movieMutableList'imdeki verileri alıyor.
    // Verileri filtreliyor.
    // movieMutableLiveData içindeki original_title'ları alır. Bu title'lar search değişkenini içerip içeriyorsa FilteredMLD'ye atar


}


//   var movieMutableList: MutableLiveData<List<GetMoviesResponse>> = MutableLiveData() yapmıyoruz
//   ÇÜNKÜ: Movie bilgilerim tutulsun istiyorum. Response.body içinde-> page,results... bilgilerinin içeriyor.
//   İstediğim şey results içindeki movielere ulaşmak bu yüzden movie türünde tutmak gerekiyor.

// EĞER GetMoviesResponse kullanırsam -> TYPE MISMATCH hatası verir.
// responseBody.results List<Movie> türünde ama mutableLiveData'ya bunu eklemem lazım. bu yüzden GetMoviesResponse yerine Movie kullan.



