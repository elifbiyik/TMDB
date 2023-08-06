package com.ex.pelicula

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ex.pelicula.api.MovieApiService
import com.ex.pelicula.db.AppDatabaseMovie
import com.ex.pelicula.db.MovieDao
import com.ex.pelicula.models.Movie
import com.ex.pelicula.repository.RepositoryMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking



/*

loadType parametresi, mevcut yükleme türünü temsil eder ve üç farklı değere sahip olabilir:
LoadType.REFRESH: Yenileme talebi, sayfanın başlangıcını temsil eder. İlk sayfayı yüklemek için kullanılır.
LoadType.PREPEND: Ön ek talebi, var olan sayfanın üzerine yeni veriler eklemek için kullanılır, ancak bu örnekte bu durumu ele almak için bir şey yapılmamıştır.
LoadType.APPEND: Ekleme talebi, mevcut sayfanın sonuna yeni veriler eklemek için kullanılır.


 */
/*

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val repo: RepositoryMovie,
    private var database: AppDatabaseMovie
) : RemoteMediator<Int, Movie>() {


    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {

        try {
            var page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    val lastKey = lastItem?.id
                    if (lastKey == null) {
                        // Yüklenen öğe yoksa, tekrar ilk sayfayı getiriyoruz
                        1
                    } else {
                        // Son sayfada devam etmek için bir sonraki sayfayı alıyoruz
                        lastKey + 1
                    }
                }
            }

         var total_pages = 10

           for (page in 1 until total_pages){
                val response = repo.getDataPopular(page.toLong())

               response.body()!!.results.

                val items = response.body()!!.results.sortedByDescending { it.vote_average } .map { item ->
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
                }
                CoroutineScope(Dispatchers.IO).launch {
                    database.movieDao().insertAll(items)
                }
           }


// response.body()!!.results -> Json türünde
// .map kullnarak JSON türünü Movie türüne dönüştürüyoruz.


            return MediatorResult.Success(endOfPaginationReached =true) // response.body()!!.results.isEmpty())
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}
*/
