package com.ex.pelicula.api

import com.ex.pelicula.data.ApiConstant
import com.ex.pelicula.models.GetMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET(ApiConstant.GET_POPULAR)
    suspend fun getPopular(
        @Query("api_key") apiKey: String = ApiConstant.API_KEY,
        @Query("page") page: Long
    ): Response<GetMoviesResponse>

    @GET(ApiConstant.GET_POPULAR)
    suspend fun getPopular1(
        @Query("api_key") apiKey: String = ApiConstant.API_KEY,
        @Query("sort_by") sortBy: String
    ): Response<GetMoviesResponse>
}

/*
Call  -------> Direkt yanıtı döndürür.
Response ----> HTTP durumunu da döndürür. ( İçerisinde yanıt, HTTP kodu (200, 500..) bilgileri vardır. )

Response kullanma sebebi :
Hata olup olmamasına göre bir sonraki sayfanın gelmesi için.
Mesela 404 döndüğünde sayfalama yapılmaz.
 */