package com.ex.pelicula.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ex.pelicula.models.FavoriteMovie
import com.ex.pelicula.models.Movie


@Dao
interface MovieDao {


    @Query("SELECT *FROM Movie")
    fun getAll(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Movie>)


    //Eğer Room'da bir tabloya yeni bir veri eklemeye çalışıyorsak ve bu veri eklenmek istenen tabloda zaten varsa,
// bu durumda bir çakışma (conflict) meydana gelir.
// Örneğin, benzersiz bir sütunla belirtilen bir tabloda aynı anahtar değeri olan bir veri zaten varsa,
// ekleme işlemi normalde başarısız olacaktır.


}