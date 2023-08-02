package com.ex.pelicula.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ex.pelicula.databinding.FragmentCommentItemBinding
import com.ex.pelicula.models.Comment

class AdapterComment(var commentList: List<Comment>) :
    RecyclerView.Adapter<AdapterComment.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCommentItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var comments = commentList[position]
        holder.bind(comments)
    }

    inner class ViewHolder(var binding: FragmentCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comments: Comment) {
            with(binding) {
                comment = comments
                executePendingBindings()

                txtName.text = comments.userEmail
                txtPoint.text = comments.point.toString()
                //           txtComment.text = comments.comment

            }
        }
    }
}