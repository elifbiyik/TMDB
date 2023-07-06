package com.ex.pelicula.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


fun ImageView.ImageLoad(url: String){

    Glide.with(context)
        .load("https://image.tmdb.org/t/p/w342$${url}")
   //     .load("https://upload.wikimedia.org/wikipedia/commons/e/e6/Android_vector.jpg") // Resim URL'si
        .into(this)
}


// Adapter ile birlikte dataBinding kullanıyoruz.
@BindingAdapter ("android:imageUrl")  // XML'de bu isimle kullanıcaz
fun downloadImage (view: ImageView, url: String) {
    view.ImageLoad(url)
}