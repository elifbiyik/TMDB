package com.ex.pelicula.repository

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.ex.pelicula.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class RepositoryUser @Inject constructor(private var auth: FirebaseAuth) {


    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("User")


    //  var userId: String? = null

    init {
        auth = FirebaseAuth.getInstance()
    }


    suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }
    // await kullandık çünkü signInWithEmailAndPassword sonucunu öğrenip öyle devam etmelyiiz.


    suspend fun signUp(email: String, password: String, name: String, lastname: String): Boolean {
        return try {
            val x = auth.createUserWithEmailAndPassword(email, password).await()

            var userName = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            auth.currentUser!!.updateProfile(userName)
            // Name bilgisini displayName'e atadık çünkü name çağıramıyoruz. displayName çağrılması gerekiyor.

            val userId = auth.currentUser!!.uid
            //   val userId = databaseReference.push().getKey()
            val user = User(email, password, name, lastname)
            databaseReference.child(userId!!).setValue(user)

            true
        } catch (e: Exception) {
            false
        }
    }


    fun getUser(): FirebaseUser? {

        return auth.currentUser

    }

    fun currentUser(): String {
        return auth.currentUser?.uid ?: ""
    }

    fun currentUserEmail(): String {
        return auth.currentUser!!.email!!
    }


    @SuppressLint("SuspiciousIndentation")
    suspend fun updateUserEmailAndName(newEmail: String, newName: String) {

        var user = auth.currentUser!!

       var email =
            user.updateEmail(newEmail)

        var newName = UserProfileChangeRequest.Builder()
            .setDisplayName(newName)
            .build()
        user.updateProfile(newName)


        Log.d("Email", email.toString())



 /*       email.addOnCompleteListener {
            if(it.isSuccessful) Log.d("UpdateEmail", "UpdateEmail")
            else Log.d("UpdateEmail", "else")
        }*/



        try {
            user.updateEmail(newEmail)
        }
        catch (e:Exception){
            Log.d("UpdateEmail", e.message.toString())
        }
    }


    fun getPhotoFor2(imageUri: Uri?): Task<Uri> {

        var uid = auth.currentUser?.uid
        val storageReference = FirebaseStorage.getInstance()
        var imageReference = storageReference.reference.child("images/$uid.jpg")

        imageUri?.let { imageReference.putFile(it) }
        var profile = UserProfileChangeRequest.Builder()
            .setPhotoUri(imageUri)
            .build()
        auth.currentUser?.updateProfile(profile)

        return imageReference.downloadUrl

    }


    fun getProfile(): Task<Uri> {
        var uid = auth.currentUser!!.uid
        val imageReference = FirebaseStorage.getInstance().reference.child("images/$uid.jpg")
        return imageReference.downloadUrl
    }

    fun signOut() {
        auth.signOut()
    }


}




/*  fun setProfilePhoto(
      bitmap: Bitmap//, imageView: ImageView, @ApplicationContext context: Context
  )  : Task<Uri>
  {

      val user = auth.currentUser!!       //com.google.firebase.auth.internal.zzx@cb747da
      val uid = user.uid                      // 3yJOVMFqyjd2CEWTSaIMq6OV9EE2

      val storageReference = FirebaseStorage.getInstance()
      var imageReference = storageReference.reference.child("images/$uid.jpg")

      val baos = ByteArrayOutputStream()
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
      val data = baos.toByteArray()

      imageReference.putBytes(data).addOnSuccessListener { upTask ->
          Log.d("TAG", "setProfilePhoto: ${upTask.task.result}")
      }.addOnFailureListener {
          Log.d("TAG", "setProfilePhoto: Error")
      }.addOnCompleteListener { upTask ->
          Log.d("TAG", "setProfilePhoto: ${upTask.result}")
      }

  //    imageReference.downloadUrl  // Resmi stroge'dan çekiyor.

      return imageReference.downloadUrl
  }
*/

// Firebaseye ekliyor.


//// resmi değiştirmek için istek !
//        var profile = UserProfileChangeRequest.Builder()
//            .setPhotoUri(imageUri)
//            .build()
//
//        user.updateProfile(profile)
//
////        return
//  //     imageReference.downloadUrl
//         /*   .addOnSuccessListener {
//            Glide.with(context)
//               .load(imageReference)
//                .into(imageView)*/
//     }


//     UserProfileChangeRequest.Builder()
//  return UserProfileChangeRequest.Builder().photoUri


//auth.createUserWithEmailAndPassword(email, password).await().user?.uid
//.user ifadesi, oturum açma işleminin başarılı olması durumunda elde edilen kullanıcı bilgilerine erişmek için kullanılır.
//?.uid ifadesi, user özelliği null olmadığı durumda, kullanıcının benzersiz kimlik (UID) değerine erişmek için kullanılır.