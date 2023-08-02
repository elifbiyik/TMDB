package com.ex.pelicula.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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

    private lateinit var bindingItem: FragmentCommentBinding
    private lateinit var bindingRecyclerView: FragmentCommentItemBinding
    private val viewModel: CommentViewModel by viewModels()
    private lateinit var adapter: AdapterComment

    lateinit var listComment: List<Comment>
    lateinit var myComment: List<Comment>

    var point = 0
    var comment = ""

    private lateinit var list: Comment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {


        bindingItem = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false)
        bindingItem.lifecycleOwner = viewLifecycleOwner

        bindingRecyclerView =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comment_item, container, false)

        var movieId = arguments?.getString("id")!!.toLong()
        var userId = arguments?.getString("userId").toString()
        var movieName = arguments?.getString("name").toString()

        bindingItem.nameMovie.text = movieName



        lifecycleScope.launch {

            listComment = viewModel.getAll(movieId)
            myComment = viewModel.getCommentAndRating(movieId, userId)

            if (myComment.isNotEmpty()) {

                bindingItem.editTextComment.setText(myComment[0].comment)
                bindingItem.rating.rating = myComment[0].point.toFloat()
                bindingItem.btnDelete.isEnabled = true


                bindingItem.btnSend.setOnClickListener {
                    var newComment = bindingItem.editTextComment.text.toString()
                    var newPoint = bindingItem.rating.rating.toInt()

                    insertOrUpdate(newComment, newPoint.toFloat(), userId, movieId)
                }

                bindingItem.btnDelete.setOnClickListener {
                    delete(myComment, movieId, userId)
                }
            }


            else {

                bindingItem.editTextComment.text.clear()
                bindingItem.rating.rating = 0.0.toFloat()
                bindingItem.btnSend.setOnClickListener {
                    lifecycleScope.launch {
                        var newComment = bindingItem.editTextComment.text.toString()
                        var newPoint = bindingItem.rating.rating.toFloat()
                        insertOrUpdate(newComment, newPoint, userId, movieId)
                    }
                }

                bindingItem.btnDelete.setOnClickListener {
                    delete(myComment, movieId, userId)

                }
            }


            viewModel.commentMutableLiveData.observe(viewLifecycleOwner, Observer {
                adapter.commentList = it
                adapter.notifyDataSetChanged()
                if (!it.isEmpty()) myComment = it
                // Listenin son halini myCommente atadım. Bu sayede dB'de yorum yokken yorum yaptıktan sonra silme işlemi yapılabildi.
            })


            adapter = AdapterComment(listComment)
            bindingItem.recyclerview.adapter = adapter
            bindingItem.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        }
        return bindingItem.root
    }


    fun delete(myComment: List<Comment>, movieId: Long, userId: String) {
        bindingItem.editTextComment.text.clear()
        bindingItem.rating.rating = 0.0.toFloat()

        if (myComment.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                val currentUserComment = myComment.find { it.userId == userId }
                if (currentUserComment != null) {
                    viewModel.delete(
                        listOf(currentUserComment),
                        movieId
                    ) // Kullanıcının kendi yorumunu sil
                }
            }
            this.myComment = emptyList()
        }
    }

    fun insertOrUpdate(newComment: String, newPoint: Float, userId: String, movieId: Long) {
        if (newComment.isNotEmpty() && newPoint != 0.0.toFloat()) {
            lifecycleScope.launch(Dispatchers.IO) {
                val getCommentAndRating =
                    viewModel.getCommentAndRating(movieId, userId)    // Kullanıcının yorum ve puanı
                if (getCommentAndRating.isNotEmpty()) {

                    viewModel.updateComment(userId, movieId, newComment, newPoint)
                } else {

                    viewModel.insert(
                        Comment(null, userId, movieId, newComment, newPoint.toInt()), movieId
                    )
                }
            }
        }
    }
}


/*


 Eğer puanlama yaptıysa yıldızlar puanlı gelicek. Neden toFloat oldu -> XML'de 1.0 olarak tutmak zorundayız ( Her bir yıldız 1.0 puan)
      var ratingdB = viewModel.rating(movieId,userId)
    if(ratingdB != null) bindingItem.rating.rating = ratingdB.toFloat()




       //Rating
         bindingItem.rating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
             Toast.makeText(requireContext(),"Point: $rating", Toast.LENGTH_SHORT).show()
         }
         // Eğer ratingi textview veya bir yerde göstermek istyiorssak bunu kullan !!!!!!

*/







