package com.ex.pelicula.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

fun ImageView.ImageLoad(url: String){

    Glide.with(context)
    //    .load("https://image.tmdb.org/t/p/w342${url}")
        .load("https://media-cdn.t24.com.tr/media/library/2023/05/1683553166317-oppenheimer-ana-afis.jpg") // Resim URL'si
        .into(this)
}

fun ImageView.ImageLoadBackground(url: String){

    Glide.with(context)
        .load("https://www.donanimhaber.com/cache-v2/?t=20221220002203&width=-1&text=0&path=https://www.donanimhaber.com/images/images/haber/158272/src/oppenheimer-ilk-fragmani-ile-sinemaseverlerin-karsisinda158272_0.jpg") // Resim URL'si
        .into(this)
}


// Adapter ile birlikte dataBinding kullanıyoruz.
@BindingAdapter ("imageUrl")  // XML'de bu isimle kullanıcaz
fun downloadImage (view: ImageView, url: String) {
    view.ImageLoad(url)
}