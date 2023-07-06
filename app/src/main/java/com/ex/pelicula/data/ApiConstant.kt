package com.ex.pelicula.data

object ApiConstant {

    // https://api.themoviedb.org/3/movie/popular?api_key=08963bf8ff23326caf0287502c99c505&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/latest?api_key=08963bf8ff23326caf0287502c99c505&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/385687?api_key=08963bf8ff23326caf0287502c99c505&language=en-US&page=1


    const val API_KEY = "08963bf8ff23326caf0287502c99c505"
    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val GET_POPULAR = "movie/popular"
    const val GET_LATEST = "movie/latest"


}


// const değişkenlere derleme zamanında değer atanır.
// val değişkenlere çalışma zamanında değer atanır.