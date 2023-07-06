package com.ex.pelicula.repository

import android.os.Bundle
import com.ex.pelicula.ui.FavoriteFragment

class RepositoryFavorite {
    //   kullanıcı değiştiğinde liste sıfırlanması için if kullanılıcak mı ? dB'ye kaydetmek gerekir.


    //private var list: MutableList<String> = mutableListOf()
    //ArrayListe göre daha çok özelliği var.

    private var list: MutableList<String> = mutableListOf()



    fun addFavorite(name: String): MutableList<String> {
        list.add(name)
        return list
    }

    fun removeFavorite(name: String): MutableList<String> {
        list.remove(name)
        return list
    }

    fun getList() {
        var fragment = FavoriteFragment()
        var bundle = Bundle()
        bundle.putStringArrayList("list", ArrayList(list))
        fragment.arguments = bundle

    }
}