package com.ex.pelicula

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.ex.pelicula.models.FavoriteMovie
import com.ex.pelicula.models.Movie
import com.ex.pelicula.repository.RepositoryMovie
import javax.inject.Inject

/*

Veri Kaynağını yönetir.

PagingSource classından türetilicek.
PagingSource -> Sayfalara ayrılmış verilerin yüklenmesi için gerekli verileri sağlar
2 parametre alır. <Key, value> ---> Key sayfa sayısı (Int), Value model class (Movie)

2 fonksiyon implement edilir.
Load() -> network istekleri, dB çağrıları yapılır.
        Belirli bir sayfa numarasına göre popüler filmleri alır ve PagingData nesnesi olarak döndürür.

----  LoadParams  ----> key ve sayfa numarası gibi değerleri tutar. ( Sayfa sayısına göre istekler yapmış oluruz. )
--- LoadResult.Page --> Apiden başarılı bir şekilde veri dönerse
--- LoadResult.Error --> Apiden başarısız bir şekilde veri dönerse

 */





class MoviePagingDataSource @Inject constructor(
    private val repo: RepositoryMovie,
    var search: String
) :
    PagingSource<Int, Movie>() {



    //Kullanıcı sayfayı yenilemek istediğinde çalışır. Sayfalama baştan başlatılır.
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val currentPage = params.key ?: 1       // İlk yüklendiğinde null olabilir bu yüzden kontrol edilir.
        val sizeItem = params.loadSize          // Her yüklenecek sayfadaki toplam öğe sayısı



        return try {

            var totalPage = repo.getDataPopular(currentPage.toLong()).body()!!.total_pages

            val response = repo.getDataPopular(currentPage.toLong())
      //      val response = repo.getPopular(totalPage)
            val responseBody = response.body()!!.results.sortedByDescending { it.vote_average }

            Log.d("Response", response.toString())

            val data =
                if(search.isNotEmpty()) responseBody.filter { it.original_title.contains(search,ignoreCase = true)}
                else responseBody



            val res = repo. getDataPopular1().body()!!.results.sortedByDescending { it.vote_average }





         LoadResult.Page(
                data = res,
                prevKey = if (currentPage == 1) null else currentPage.minus(1),
                nextKey = currentPage.plus(1)
            )



        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}

// val data = response.body()!!.results.sortedByDescending { it.vote_average }
// Her sayfayı kendi arasında sıralıyor.


// BÜTÜN FİLMLERİ ÇEK - LİSTELE - SONRA PAGİNG YAP