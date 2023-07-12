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
class SignUpViewModel @Inject constructor(
    private val repo: RepositoryUser,
    @ApplicationContext private val context: Context

    ) : ViewModel() {


    var signUpRes = MutableLiveData<Boolean>()

    suspend fun signUp(email: String, password: String, name: String, lastname: String) {

        if (email.isNullOrBlank() || password.isNullOrBlank() || name.isNullOrBlank() || lastname.isNullOrBlank()) {
            Toast.makeText(
                context,
                "Please enter your email, password, name or last name",
                Toast.LENGTH_SHORT
            ).show()
        } else if (password.length < 6) {
            Toast.makeText(
                context,
                "Your password must consist of 6 digits only.",
                Toast.LENGTH_SHORT
            ).show()
        } else {

            val isValid = repo.signUp(email, password, name, lastname)
            signUpRes.value = isValid

        }
    }
}


/*
  class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
          if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
              return SignUpViewModel(context) as T
          }
          throw IllegalArgumentException("Unknown ViewModel class")
      }
 }
  */


/*
         fun signUp1(
             //email: String, password: String, name:String, lastname : String
         ){

             var email = emailMLD.value.toString()
             var password = passwordMLD.value.toString()
             var name = nameMLD.value.toString()
             var lastName = lastNameMLD.value.toString()



             if (email.isNullOrBlank() || password.isNullOrBlank() || name.isNullOrBlank() || lastName.isNullOrBlank()) {
                 Toast.makeText(
                     context,
                     "Please enter your email, password, name or last name",
                     Toast.LENGTH_SHORT
                 ).show()
             } else if (password.length < 6) {
                 Toast.makeText(
                     context,
                     "Your password must consist of 6 digits only.",
                     Toast.LENGTH_SHORT
                 ).show()
             } else {

                 val isValid = repo.signUp(email, password, name, lastName)
                 signUpRes.value = isValid

             }
         }
*/
