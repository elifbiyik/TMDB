package com.ex.pelicula.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.ex.pelicula.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
            auth.signInWithEmailAndPassword(email.toString(), password.toString()).await()
            true
        } catch (e: Exception) {
            false
        }
    }
    // await kullandık çünkü signInWithEmailAndPassword sonucunu öğrenip öyle devam etmelyiiz.


    suspend fun signUp(email: String, password: String, name: String, lastname: String): Boolean {
        return try {
            val x = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = databaseReference.push().getKey()
            val user = User(email, password, name, lastname)
            databaseReference.child(userId!!).setValue(user)
            Log.d(TAG, "userId = $userId")
            true
        } catch (e: Exception) {
            false
        }
    }
}


/*


    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        callback: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }


    suspend fun signUp1(email: String, password: String, name: String, lastname: String): Boolean {

        // launch kullanırsam; result false olarak başlar -> auth.create .. ile başlayan kısım 3 saniye bekler ama bu bekleme süresibitene kadar false dönmüş olur.
        // Çünkü launch dediğimizde o kısım bekliyor ama ondan sonraki kısımlar devam ediyor. ( Yani result = true olana kadar retunr false olmuş olucak )

        //runBlocking yapıp delay(1000) veremyiz.( Defterdeki örnek gibi).
        // Çünkü firebase işlemleri asenkton yapıda işlemler.
        // runBlocking'i SENKRON İŞLEMLERDE KULLANABİLİRİM
        // Senkron işlem veya asenkron işlem olduğunu nasıl anlarım? ->

        var result = false


        val x = auth.createUserWithEmailAndPassword(email, password)
        x.await()
        //          .addOnCompleteListener { task ->
        if (x.isSuccessful) {
            userId = databaseReference.push().getKey()
            val user = User(email, password, name, lastname)
            databaseReference.child(userId!!).setValue(user)
            Log.d(TAG, "userId = $userId")
            result = true


        }

        return result

*/

/*

//      var databaseReference = FirebaseDatabase.getInstance().getReference("User")
var user = User(email, password, name, lastname)

userId = databaseReference.push().getKey()
databaseReference.child(userId.toString()).setValue(user)
*/

// eğer kaydedilmezse false döndür.


//auth.createUserWithEmailAndPassword(email, password).await().user?.uid
//.user ifadesi, oturum açma işleminin başarılı olması durumunda elde edilen kullanıcı bilgilerine erişmek için kullanılır.
//?.uid ifadesi, user özelliği null olmadığı durumda, kullanıcının benzersiz kimlik (UID) değerine erişmek için kullanılır.