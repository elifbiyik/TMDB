package com.ex.pelicula.viewModel


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ex.pelicula.repository.RepositoryUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: RepositoryUser,
    @ApplicationContext private val context: Context
) : ViewModel() {

    // Toast mesajları için context lazım @ApplicationContext diyip uygulamanın contexi oluşturuluyor


    var res = MutableLiveData<Boolean>()

    suspend fun signIn(email: String, password: String) {

        //Bunu suspend yapmak yerine viewModelScope.launch { } kullanılır mı ?

        val isValid = repo.signIn(email, password)

        if (email.toString().isNullOrBlank() || password.toString().isNullOrBlank()) {
            Toast.makeText(
                context,
                "Please enter your email and password",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if (!isValid) {
            Toast.makeText(
                context,
                "Email or password is wrong",
                Toast.LENGTH_SHORT
            ).show()
        }
        else {
            res.value = isValid

        }
    }
}





    /*

fun signIn1(email: String, password: String): Boolean {
    var result = false
    repo.signIn1(email, password) { isValid ->
        result = isValid
    }
    return result
}
*/


    /* fun signInWithEmailAndPassword(
     email: String,
     password: String,
     callback: (Boolean, String?) -> Unit
 ) {
     viewModelScope.launch(Dispatchers.IO) {
         repo.signInWithEmailAndPassword(email, password) { success, error ->
             callback(success, error)
         }
     }
 }*/
