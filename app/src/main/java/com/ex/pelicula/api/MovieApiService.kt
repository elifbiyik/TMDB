package com.ex.pelicula.api

import com.ex.pelicula.data.ApiConstant
import com.ex.pelicula.models.GetMoviesResponse
import com.ex.pelicula.models.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    //https://api.themoviedb.org/3/movie/popular?api_key=08963bf8ff23326caf0287502c99c505&language=en-US&page=1


    /*
    @GET(ApiConstant.GET_POPULAR)
    fun getPopular(
        @Query("api_key") apiKey: String = ApiConstant.API_KEY,
        @Query("page") page: Int
    ): Call<List<GetMoviesResponse>>

// !!! List ile yapınca hata veriyor.
// " Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path $ " hatası (VM'de)


// ???????????? HATANIN SEBEİ
//GetMoviesResponse'dan liste dönmüyor. Result içinde liste var. EĞER Movie dönmürmek isteseydim List kullanıcaktım.

*/




    //    const val GET_LATEST = "movie/latest"
    @GET(ApiConstant.GET_POPULAR)
    fun getPopular(
        @Query("api_key") apiKey: String = ApiConstant.API_KEY,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
// ......com/movie/popular?api_key=657575&page=1


}















// List<Movie> yerine Movie kullan. ÇÜNKÜ : Liste'de her sayfa için yeni istek gerekir.