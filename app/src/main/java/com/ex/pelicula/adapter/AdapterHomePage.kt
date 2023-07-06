package com.ex.pelicula.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentHomePageBinding
import com.ex.pelicula.models.Movie
import com.ex.pelicula.ui.DetailFragment


class AdapterHomePage(var movieList: List<Movie>) : RecyclerView.Adapter<AdapterHomePage.ViewHolder>()
//    ,setOnClickListener
{

    // Liste itemlerini tutucak ama XML'de yaptığımız için gerek var mı ?
    class ViewHolder(var binding: FragmentHomePageBinding) : RecyclerView.ViewHolder(binding.root){
        /*        val name = binding.tvName
                val image = binding.imageView
                val vote_average = binding.tvVoteAverage*/

/*        init {
            itemView.setOnClickListener(this)
        }*/

/*        override fun onClick(v: View?) {
            val position = adapterPosition

            var fragment = DetailFragment()
            var bundle = Bundle()
            bundle.putString("name", movieList[position].original_title)

            fragment.arguments = bundle

            val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.constraint, fragment)
            transaction.addToBackStack(null)
            transaction.commit()


        }*/
        /*

                fun bind(item: ClipData.Item) {
                    binding.tvName.text = item.text
                }
        */

/*


        fun onClick(v: View) {


            var fragment = DetailFragment()
            var bundle = Bundle()
            bundle.putString("name", .original_title)
            bundle.putString("vote_average", movie.vote_average)
            bundle.putString("overview", movie.overview)
            fragment.arguments = bundle

        }
*/


    }

    init {
        movieList = ArrayList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FragmentHomePageBinding>(
            inflater,
            R.layout.fragment_home_page,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var movie = movieList[position]

        holder.binding.movie = movie        //XML'deki movie'yi kullanarak bağladı


        holder.itemView.setOnClickListener {
            /*       try {
                        val intent = Intent(holder.itemView.context, DetailFragment::class.java)
                        intent.putExtra("name", movie.original_title)
                        holder.itemView.context.startActivity(intent)
                    }
                    catch (e:Exception){
                        Log.d("hata", e.message.toString())
                    }
         */

            var fragment = DetailFragment()
            var bundle = Bundle()
            bundle.putString("name", movie.original_title)
            bundle.putString("vote_average", movie.vote_average)
            bundle.putString("overview", movie.overview)
            fragment.arguments = bundle

            Log.d("hata1", movie.original_title)

            try {

                //context as AppCompatActivity

                //  val context = holder.itemView.context//.applicationContext // adapter view'ine ihtiyaç yok.?

                val context = holder.binding.root.context

              /*  val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.constraint, fragment)
                    .addToBackStack(null)
                    .commit()*/
                val transaction = (context as FragmentActivity)
                    .supportFragmentManager.beginTransaction()

                transaction.replace(R.id.constraint, DetailFragment())
                    .addToBackStack(null)
                    .commit()



            } catch (e: Exception) {
                Log.d("hata", e.message.toString())
            }
        }
    }
//    com.ex.pelicula.di.MyApplication cannot be cast to androidx.appcompat.app.AppCompatActivity
 //   dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper cannot be cast to androidx.fragment.app.FragmentActivity



    /*
        override fun click(view: View) {
            val movie = view.tag as Movie
            val intent = Intent(view.context, DetailFragment::class.java)
            intent.putExtra("name", movie.original_title)
            intent.putExtra("vote_average", movie.vote_average.toString())
            view.context.startActivity(intent)
        }*/


}


/*
 @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var movie = movieList[position]
        /*      holder.name.text = movie.original_title
                 holder.vote_average.text = movie.vote_average.toString()
         */
        holder.binding.movie = movie        //XML'deki movie'yi kullanarak bağladı
        //     holder.binding.listener = this

        /*   Glide.with(holder.itemView.context)
    //           .load("https://upload.wikimedia.org/wikipedia/commons/e/e6/Android_vector.jpg") // Resim URL'si
               .load("https://image.tmdb.org/t/p/w342${movie.poster_path}")
               .into(holder.binding.imageView)
   */

        holder.itemView.setOnClickListener {
            /*       try {
                        val intent = Intent(holder.itemView.context, DetailFragment::class.java)
                        intent.putExtra("name", movie.original_title)
                        holder.itemView.context.startActivity(intent)
                    }
                    catch (e:Exception){
                        Log.d("hata", e.message.toString())
                    }
         */

            var fragment = DetailFragment()
            var bundle = Bundle()
            bundle.putString("name", movie.original_title)
            bundle.putString("vote_average", movie.vote_average)
            bundle.putString("overview", movie.overview)
            fragment.arguments = bundle

            Log.d("hata1", movie.original_title)

            try {

                //context as AppCompatActivity

                //  val context = holder.itemView.context//.applicationContext // adapter view'ine ihtiyaç yok.?

                val context = holder.binding.root.context

              /*  val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.constraint, fragment)
                    .addToBackStack(null)
                    .commit()*/
                val transaction = (context as FragmentActivity)
                    .supportFragmentManager.beginTransaction()

                transaction.replace(R.id.constraint, DetailFragment())
                    .addToBackStack(null)
                    .commit()



            } catch (e: Exception) {
                Log.d("hata", e.message.toString())
            }
        }
    }
 */

