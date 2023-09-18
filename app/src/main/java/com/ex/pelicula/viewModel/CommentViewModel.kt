package com.ex.pelicula.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.ex.pelicula.models.Comment
import com.ex.pelicula.repository.RepositoryComment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel()
class CommentViewModel @Inject constructor(var repo: RepositoryComment) : ViewModel() {

    var commentMutableLiveData = MutableLiveData<List<Comment>>()

    fun insert(comment: Comment, movieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insert(comment)
            viewModelScope.launch(Dispatchers.Main) {
                commentMutableLiveData.value = repo.getAll(movieId)
            }
        }
    }

    fun delete(list: List<Comment>, movieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(list)
            viewModelScope.launch(Dispatchers.Main) {
                commentMutableLiveData.value = repo.getAll(movieId)
            }
        }
    }

    suspend fun getAll(movieId: Long): List<Comment> {
    return withContext(Dispatchers.IO) {
            repo.getAll(movieId)
        }
    }

    suspend fun getCommentAndRating(movieId: Long, userId: String): List<Comment> {
     return withContext(Dispatchers.IO) {
            repo.getCommentAndRating(movieId, userId)
        }
   }

    fun updateComment(userId: String, movieId: Long, comment: String, point: Float, userEmail : String) {
        repo.updateComment(userId, movieId, comment, point, userEmail)
  // GetAll çağırmazsam textviewde comment'i güncellediğimde recyclerView'de güncellenmiyor. Ekrandan çıkıp girmek gerekiyor.
        // viewModelScope.launch -> GetAll suspen olduğu için kullanıyoruz.
           viewModelScope.launch {
               commentMutableLiveData.value = repo.getAll(movieId)

           }
    }
}
