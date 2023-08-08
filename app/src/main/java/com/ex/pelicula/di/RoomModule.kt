package com.ex.pelicula.di

import android.content.Context
import androidx.room.Room
import com.ex.pelicula.db.AppDatabaseFavorite
import com.ex.pelicula.db.AppDatabaseComment
import com.ex.pelicula.db.AppDatabaseMovie
import com.ex.pelicula.db.DaoComment

import com.ex.pelicula.db.DaoFavorite
import com.ex.pelicula.db.DaoMovie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabaseFavorite {
        return Room.databaseBuilder(context, AppDatabaseFavorite::class.java, "favorite.db").fallbackToDestructiveMigration()
            .build()
    }


    // databaseBuilder ->  yapılandırma seçenkeleri
// fallbackToDestructiveMigration -> yapılacak değişikliklerle ilgili. Her şema değişikliğinde eski veriler silinir ve baştan yapılır.

    // Uygulamada veritabanı işlemleri yapmak için kullanılır.
    // Context kullanabilmek için @ApplicationContext context : Context yazılmak zorunda!!

    // AppDatabase::class.java -> abstract sınıf veya interface olmalıdır.
// favorite.db -> Oluşturulan veritabanının adıdır.( SQLite veritabanı dosyasının adı )
    // ygulama içinde favorite.db ile erişilir.


    @Provides
    @Singleton
    fun provideFavoriteDao(appDatabaseFavorite: AppDatabaseFavorite): DaoFavorite {
        return appDatabaseFavorite.favoritedao()
    }

    @Provides
    @Singleton
    fun providesAppDatabaseComment (@ApplicationContext context: Context) : AppDatabaseComment {
        return Room.databaseBuilder(context, AppDatabaseComment :: class.java, "comment.db").fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun providesCommentDao (appDatabasecomment : AppDatabaseComment) : DaoComment {
        return appDatabasecomment.commentDao()
    }


    @Provides
    @Singleton
    fun providesAppDatabaseMovie(@ApplicationContext context: Context) : AppDatabaseMovie {
        return Room.databaseBuilder(context, AppDatabaseMovie::class.java, "movie.db").fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesMovieDao(appDatabaseMovie: AppDatabaseMovie) : DaoMovie{
        return appDatabaseMovie.movieDao()
    }



}
