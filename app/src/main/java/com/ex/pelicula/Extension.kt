package com.ex.pelicula.util

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ex.pelicula.R

fun ImageView.ImageLoad(url: String) {

    Glide.with(context)
        .load("https://image.tmdb.org/t/p/w342${url}")
        .into(this)
}

// Adapter ile birlikte dataBinding kullanıyoruz.
@BindingAdapter("imageUrl")  // XML'de bu isimle kullanıcaz
fun downloadImage(view: ImageView, url: String) {
    view.ImageLoad(url)
}



/*
fun ImageView.Color(colorful: String) {
    setColorFilter(ContextCompat.getColor(context, R.color.colorful))
}
*/