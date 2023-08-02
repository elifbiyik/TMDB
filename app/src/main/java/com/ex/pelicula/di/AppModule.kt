package com.ex.pelicula.di

import com.ex.pelicula.api.MovieApiService
import com.ex.pelicula.data.ApiConstant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModuleAppModule {

// Provides -> Bağımlılıkları oluşturmak için


    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }


    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    // OkHttp -> HTTP istekleri oluşturmak ve sunucudan gelen yanıtları işlemek için kullanılır
    // Retrofit için OkHttpClient kullan
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }








}



 // ???? Create bazı yerlerde farklı fonksiyon içinde yazılmış

//@InstallIn(ApplicationComponent::class) - uygulama başlatıldığında oluşur ve bitene kadar aktif
//                                        - uygulama seviyesinde paylaşılması gereken bir bileşense kullan

// Hilt'e bu bileşen üzerinde çalışmasını söyledik
// Hilt uygulama bileşenlerinin bağımlılıklarını otomatik olarak oluşturup yönetebilir.

// Provides
// bir bağımlılığın nasıl oluşturulacağını belirtir.
// Her bir @Provides işlevi, bir bağımlılığın nasıl sağlanacağını tanımlar.


/*
@Singleton
@Provides
fun provideApplication(@ApplicationContext app : Context): Application {
    return app as Application
}
//uygulamanın farklı bileşenlerinde bu bağımlılık enjekte eilebilir

*/
