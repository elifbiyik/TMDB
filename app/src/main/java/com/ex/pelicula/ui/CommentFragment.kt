package com.ex.pelicula.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.pelicula.R
import com.ex.pelicula.adapter.AdapterComment
import com.ex.pelicula.databinding.FragmentCommentBinding
import com.ex.pelicula.databinding.FragmentCommentItemBinding
import com.ex.pelicula.models.Comment
import com.ex.pelicula.viewModel.CommentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentFragment : Fragment() {


    private lateinit var bindingItem: FragmentCommentItemBinding
    private lateinit var bindingRecyclerView: FragmentCommentBinding
    private val viewModel: CommentViewModel by viewModels()
    private lateinit var adapter: AdapterComment

    lateinit var listComment : List<Comment>
    var point = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        bindingItem =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comment_item, container, false)
        bindingItem.lifecycleOwner = viewLifecycleOwner

        bindingRecyclerView =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false)


        var movieId = arguments?.getString("id")!!.toLong()
        var userId = arguments?.getString("userId").toString()
        var movieName = arguments?.getString("name").toString()


        bindingItem.nameMovie.text = movieName

 /*
        //Rating
          bindingItem.rating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
              Toast.makeText(requireContext(),"Point: $rating", Toast.LENGTH_SHORT).show()
          }
          // Eğer ratingi textview veya bir yerde göstermek istyiorssak bunu kullan !!!!!!
  */




        lifecycleScope.launch(Dispatchers.IO) {
            listComment = viewModel.getAll(movieId)
            Log.d("listComment", listComment.toString())


            try {
                adapter = AdapterComment(listComment)
                bindingRecyclerView.recyclerview.adapter = adapter
                bindingRecyclerView.recyclerview.layoutManager = LinearLayoutManager(requireContext())
            }
            catch (e:Exception){
                Log.d("listCommenthata", e.message.toString())
            }




            bindingItem.btnSend.setOnClickListener {

                point = bindingItem.rating.rating.toInt()       // rating itemimizin rating bilgisine eriştik
                var comment = bindingItem.editTextComment.text.toString()

                bindingItem.editTextComment.text.clear()


                var list = Comment(
                    null,
                    userId, // Herkeste görülmesi için userId vermesem ??
                    movieId,
                    comment,
                    point
                )

                lifecycleScope.launch(Dispatchers.IO) { viewModel.insert(list, movieId) }

                Toast.makeText(requireContext(), "Add", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.commentMutableLiveData.observe(viewLifecycleOwner, Observer {
            adapter.commentList = it
            adapter.notifyDataSetChanged()
            if(!it.isEmpty()) Toast.makeText(requireContext(), "commentMutableLiveData", Toast.LENGTH_SHORT).show()
            else Toast.makeText(requireContext(), "FailcommentMutableLiveData", Toast.LENGTH_SHORT).show()
        })

        return bindingItem.root
    }


}