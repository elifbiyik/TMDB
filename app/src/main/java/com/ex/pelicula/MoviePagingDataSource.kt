/*
package com.ex.pelicula

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.ex.pelicula.models.GetMoviesResponse
import com.ex.pelicula.models.Movie
import com.ex.pelicula.repository.RepositoryMovie
import javax.inject.Inject


*/
/*
PagingSource classından türetilicek.
2 parametre alır. <Key, value> ---> Key sayfa sayısı (Int), Value model class (Movie)

2 fonksiyon implement edilir.
Load() -> network istekleri, dB çağrıları yapılır.

----  LoadParams  ----> key ve sayfa numarası gibi değerleri tutar. ( Sayfa sayısına göre istekler yapmış oluruz. )
--- LoadResult.Page --> Apiden başarılı bir şekilde veri dönerse
--- LoadResult.Error --> Apiden başarısız bir şekilde veri dönerse

 *//*





class MoviePagingDataSource @Inject constructor (private val repo: RepositoryMovie) :
    PagingSource<Int, Movie>() {


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val currentPage = params.key ?: 1

        return try {

            val response = repo.getDataPopular(currentPage)
            val data = response.body()!!.results
            LoadResult.Page(
                data = data,
                prevKey = if (currentPage ==1) null else currentPage.minus(1),
                nextKey = currentPage.plus(1)
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }


    }


}
*/
