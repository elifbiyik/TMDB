package com.ex.pelicula.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ex.pelicula.models.FavoriteMovie



@Database(entities = [FavoriteMovie::class], version = 11)
abstract class AppDatabaseFavorite : RoomDatabase() {
     abstract fun favoritedao() : DaoFavorite

}

// allowMainThreadQueries() -> ana iş parçacığında veritabanı sorgularının yapılmasına izin verir


// Room'da karışık dB işlemleri olduğu için abstract olarak tanımladık ??
// abstract silince 3 tane fonksiyonu implement ettiriyor.

// FavoriteDao -> interface
// İnterface'den kalıtım aldık bu sayede favoritedao ile insert, delete, select işlemlerini yapabilirz.





