package com.ex.pelicula.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.pelicula.models.Comment
import com.ex.pelicula.repository.RepositoryComment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel()
class CommentViewModel @Inject constructor(
    var repo: RepositoryComment //, var repoUser : RepositoryUser
) : ViewModel() {


    var commentMutableLiveData = MutableLiveData<List<Comment>>()


    /*  fun getUser(){

          repoUser.getUser()
      }*/


    fun insert(comment: Comment, movieId: Long) {
        viewModelScope.launch {
            repo.insert(comment)
            commentMutableLiveData.value = repo.getAll(movieId)
        }
    }

    fun delete(comment: Comment) {
        viewModelScope.launch {
            //       commentMutableLiveData.value = repo.delete(comment)
        }
    }

    suspend fun getAll(movieId: Long): List<Comment> {

        return withContext(Dispatchers.IO) {
            repo.getAll(movieId)
        }
    }


}
