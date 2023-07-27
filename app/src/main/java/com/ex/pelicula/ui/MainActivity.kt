package com.ex.pelicula.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ex.pelicula.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNavigationView) as BottomNavigationView
        bottomNav.setOnItemSelectedListener{
            when(it.itemId){
                R.id.home -> {
                    loadFragment(HomePageFragment())
                    true
                }
               R.id.fav -> {
                    loadFragment(FavoriteFragment())
                    true
                }
                R.id.account -> {
                    loadFragment(AccountFragment2())
                    true
                }

                else -> false
            }
        }


        loadFragment(SignInFragment())


    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.constraint,fragment)
        transaction.commit()
    }

}