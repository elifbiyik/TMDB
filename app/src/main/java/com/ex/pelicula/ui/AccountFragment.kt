package com.ex.pelicula.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentAccountBinding
import com.ex.pelicula.viewModel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/*

Bitmap: görüntü verilerini (resim) tutmak ve işlemek için kullanılan bir sınıftır.
Uri: kaynaklara (dosyalara, veritabanlarına, web sitelerine vb.)
benzersiz bir şekilde erişmek için kullanılan bir kimliklendiricidir.



Android'de Bitmap, görüntüleri göstermek, işlemek ve manipüle etmek için yaygın olarak kullanılır.
Android'de, Uri sınıfı, medya dosyalarına, içerik sağlayıcılara, web adreslerine ve diğer kaynaklara işaret etmek için kullanılır.

Firebase Storage gibi hizmetlere görüntü yüklemek istediğinizde, genellikle Uri tipinde bir konum belirtmeniz gerekir.

 */


@AndroidEntryPoint
class AccountFragment : Fragment() {

    private val viewModel: AccountViewModel by viewModels()
    private lateinit var binding: FragmentAccountBinding

    private var imageUri: Uri? = null // Fotoğrafın adresi

    private val REQUEST_IMAGE_GALLERY = 1
    private val REQUEST_IMAGE_CAMERA = 2
    private val PERMISSION_REQUEST_CODE = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


// İzin verilip verilmediğini kontrol ediyor.
            /*
            if (ContextCompat.checkSelfPermission(
                     requireContext(),
                     android.Manifest.permission.READ_EXTERNAL_STORAGE
                 )
                 != PackageManager.PERMISSION_GRANTED
             ) {
                 ActivityCompat.requestPermissions(
                     requireActivity(),
                     arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                     PERMISSION_REQUEST_CODE
                 )

             }
             */
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        binding.lifecycleOwner = this

        viewModel.getUser()

        binding.accEmail.isEnabled = false
        binding.accName.isEnabled = false



        binding.edit.setOnClickListener {


            var newEmail = binding.accEmail.text.toString()
            var newName = binding.accName.text.toString()

            if (binding.edit.text == "Done") {
                binding.accEmail.isEnabled = false
                binding.accName.isEnabled = false

                binding.edit.text = "Edit"
                binding.editImage.visibility = View.GONE



                lifecycleScope.launch {
                    viewModel.updateUser(newEmail, newName) // Firebaseye kaydetme işlemi
                    Log.d("imageuri", imageUri.toString())
                    viewModel.updateUserProfilePhoto(imageUri?: Uri.EMPTY)
                }
            } else {
                binding.accEmail.isEnabled = true
                binding.accName.isEnabled = true

                binding.edit.text = "Done"
                binding.editImage.visibility = View.VISIBLE

            }
        }


        binding.editImage.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Select Image")
            builder.setMessage("Choose your option")

            builder.setPositiveButton("Gallery") { dialog, which ->
                dialog.dismiss()

                //        val intent  = Intent()
                //        intent.type = "images/*"
                //        intent.action = Intent.ACTION_GET_CONTENT
// Yukardaki 3 kodu kullanıp aşağıdaki intent kodunu kullanmazsan ; Galeri değil Dosya açılıyor.
//!!! ACTION_GET_CONTENT bütün dosyalara erişmeyi sağlıyor biz tipini (images/*) belirterek resim seçmesini sağlıyoruz.
// Request codu kullanmak ; Birden fazla intent varsa o intentleri birbirinden ayırmak için sabit sayı kullanırız. (Sallayabilirsin sayıyı 3, 5 .. farketmez)


                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
            } // Uri bilgisi saklanıyor. bunu çekmeye çalışıcaz. onActivityResult ile uri'yi alıyoruz. (Aşağıda)

            /* builder.setNegativeButton("Camera") { dialog, which ->
                 dialog.dismiss()
                 val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                 startActivityForResult(intent, REQUEST_IMAGE_CAMERA)

             }*/

            val dialog: AlertDialog = builder.create()
            dialog.show()


        }



        viewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer { user ->
            if (user !== null) {

                binding.accEmail.setText(user.email)
                binding.accName.setText(user.displayName)
                Glide.with(requireContext())
                    .load(user.photoUrl)
                    .into(binding.accIm)

            } else Toast.makeText(context, " Null", Toast.LENGTH_SHORT).show()
        })


        viewModel.profilMutableLiveData.observe(viewLifecycleOwner, Observer { uri ->

            if (uri != null) {
                Glide.with(requireContext())
                    .load(uri)
                    .into(binding.accIm)
            } else Toast.makeText(context, "Uri boş", Toast.LENGTH_SHORT).show()
        })







        return binding.root

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//RESULT_OK = Yaptıımız iş başarılıysa. Kullanıcı gerçekten image seçtiyse
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_GALLERY -> {
                    val selectedImageUri: Uri? = data?.data  //Seçilen resmin urisi alınır

                    binding.accIm.setImageURI(selectedImageUri)
                    imageUri = selectedImageUri!!
                }

                /* REQUEST_IMAGE_CAMERA -> {
                     val imageBitmap = data?.extras?.get("data") as Bitmap
                     imageUri = saveImageToFile(imageBitmap)!!
                     binding.accIm.setImageBitmap(imageBitmap)
                 }*/
            }
        }
    }

    private fun saveImageToFile(bitmap: Bitmap): Uri? {

        val imagesDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(imagesDir, "profile_image.jpg")
        try {
            val stream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            stream.flush()
            stream.close()
            return FileProvider.getUriForFile(
                requireContext(),
                "com.ex.pelicula.fileprovider",
                imageFile
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}


//onActivityResult : eski ve geleneksel startActivityForResult ve onActivityResult yöntemleridir
//registerForActivityResult : Android Jetpack ActivityResult API'sini kullanarak daha basit ve düzenli bir şekilde sonuç alışverişi yapmanızı sağlar.




