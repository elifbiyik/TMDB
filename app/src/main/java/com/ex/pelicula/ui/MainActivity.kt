package com.ex.pelicula.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentHomePageBinding
import com.ex.pelicula.databinding.FragmentSignInBinding
import com.ex.pelicula.repository.RepositoryUser
import com.ex.pelicula.viewModel.DetailViewModel
import com.ex.pelicula.viewModel.SignInViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView

    private val viewModel: SignInViewModel by viewModels()


    /*
        public override fun onStart() {
            super.onStart()
            val user = viewModel.getCurrentUser()
            if (user != null) {
                loadFragment(HomePageFragment())
            } else {
                loadFragment(SignInFragment())
            }
        }

    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var useruid = viewModel.getCurrentUserUID()

        if (useruid != "") {
            //     MODE_PRIVATE -> Bilgilere bu uygulama dışında kimse erişemez.
            //     MODE_APPEND -> Önceki veri üstüne ekleme yapıyor.

            var sharedPreferences = this.getSharedPreferences("user", MODE_PRIVATE)
            var editor =
                sharedPreferences.edit()           //Veri ekleme, kaydetme işlemleri yapmak için oluşturduk
            editor.putString("uid", useruid).apply()

            loadFragment(HomePageFragment())
        } else
            loadFragment(SignInFragment())



        bottomNav = findViewById(R.id.bottomNavigationView) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
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


        //     loadFragment(SignInFragment())


    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.constraint, fragment)
        transaction.commit()
    }

}