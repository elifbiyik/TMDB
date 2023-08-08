package com.ex.pelicula.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ex.pelicula.repository.RepositoryUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class AccountViewModel2 @Inject constructor(
    private val repo: RepositoryUser,
    @ApplicationContext private val appContext: Context
) : ViewModel() {


    var userMutableLiveData = MutableLiveData<FirebaseUser?>()

    var profilMutableLiveData = MutableLiveData<Uri>()


    fun getUser() {

        var user = repo.getUser()
        userMutableLiveData.postValue(user)

        Log.d("userIdUser", user.toString())

    }

    suspend fun updateUser(newEmail: String, newName: String) {
        repo.updateUserEmailAndName(newEmail, newName)

    }


    fun updateUserProfilePhoto(imageUri: Uri?) {

        //Type mismatch: inferred type is Task<Uri> but Uri? was expected
        //      profilMutableLiveData.value = repo.getPhotoFor2(imageUri)     // Neden böyle yazarken hata veriyorda aşağıdaki gibi yazınca vermiyor ?

        //MLD'de googleden gidebilmelik adresi kaydediyor

          repo.getPhotoFor2(imageUri).addOnSuccessListener { uri ->
               profilMutableLiveData.value = uri
           } .addOnFailureListener { exception ->
               Log.d("HataVM", "Hata")
           }
        }



    fun getProfil() {
        repo.getProfile().addOnSuccessListener {
            profilMutableLiveData.value = it
        }
    }

    fun signOut(){
        repo.signOut()
    }


}