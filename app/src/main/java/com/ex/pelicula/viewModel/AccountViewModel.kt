package com.ex.pelicula.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ex.pelicula.repository.RepositoryUser
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class AccountViewModel @Inject constructor(
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

    fun updateUser(newEmail: String, name: String) {
        repo.updateUserEmailAndName(newEmail, name)

    }


    fun updateUserProfilePhoto(
        imageUri: Uri //, imageView : ImageView, @ApplicationContext context: Context
    ) {

        // Seçilen resmin urisini kullanarak bitmap nesnesi alır.
        // Bitmap yaptık çünkü : Resimleri işlemek veya görüntülemek için Android'de Bitmap nesneleri yaygın olarak kullanılır.
        val bitmap = MediaStore.Images.Media.getBitmap(
            appContext.contentResolver,
            imageUri
        )
        val originHeight = bitmap.height
        val originWidth = bitmap.width
        val isPortrait = originHeight > originWidth
        var newWidth = 0
        var newHeight = 0
        var scaleRate = 0f
        if (isPortrait) {
            scaleRate = 300f / originHeight
        } else {
            scaleRate = 300f / originWidth
        }
        newHeight = Math.round(originHeight * scaleRate)
        newWidth = Math.round(originWidth * scaleRate)

        val resizedBitmap = Bitmap.createScaledBitmap(
            bitmap,
            newWidth,
            newHeight,
            true
        )



     //   userMutableLiveData.value = repo.setProfilePhoto(resizedBitmap)
        // profilMutableLiveData.value = repo.setProfilePhoto(resizedBitmap)

    }


}