package com.ex.pelicula.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentAccountBinding
import com.ex.pelicula.viewModel.AccountViewModel2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AccountFragment2 : Fragment() {


    private val viewModel: AccountViewModel2 by viewModels()
    private lateinit var binding: FragmentAccountBinding


    private var imageUri: Uri? = null // Fotoğrafın adresi

    private val REQUEST_IMAGE_GALLERY = 1
    private val REQUEST_IMAGE_CAMERA = 2
    private val PERMISSION_REQUEST_CODE = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }



// onPause -> Kullanıcı arayüzünden çıkıldığında yaypılan işlemler
// Galeriden resim seçmek için arayüzden çıkılıyor. AMA SEÇTİĞİM RESMİN DEĞİL ÖNCEKİ RESMİN GELMESİ İLE İLGİSİ NE ?
    override fun onPause() {
        super.onPause()

        lifecycleScope.launch {
            viewModel.updateUserProfilePhoto(imageUri)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        binding.lifecycleOwner = this


        viewModel.getUser()   // Sayfa ilk açıldığında email ve name gelmesini sağladık.
        viewModel.getProfil()


        binding.accEmail.isEnabled = false
        binding.accName.isEnabled = false


        binding.signOut.setOnClickListener {

            viewModel.signOut()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.constraint, SignInFragment()).commit()
        }




        binding.edit.setOnClickListener {


            var newEmail = binding.accEmail.text.toString()
            var newName = binding.accName.text.toString()

            if (binding.edit.text == "Done") {
                binding.accEmail.isEnabled = false
                binding.accName.isEnabled = false

                binding.edit.text = "Edit"
                binding.editImage.visibility = View.GONE

                viewModel.updateUser(newEmail, newName) // Firebaseye kaydetme işlemi


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

                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
            } // Uri bilgisi saklanıyor. bunu çekmeye çalışıcaz. onActivityResult ile uri'yi alıyoruz. (Aşağıda)

            // ACTION_PICK -> belirli bir içerik türünü ve belirli bir veri türünü seçmek için kullanıl
            // MediaStore.Images.Media.EXTERNAL_CONTENT_URI -> Galeriden seçmek için
            // startActivityForResult -> Galeri seçiminin sonucunu alırız. (Uri bilgsini)

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }




        viewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer { user ->
            if (user !== null) {

                binding.accEmail.setText(user.email)
                binding.accName.setText(user.displayName)


            } else Toast.makeText(context, " Null", Toast.LENGTH_SHORT).show()
        })
        viewModel.profilMutableLiveData.observe(viewLifecycleOwner, Observer { uri ->
            Glide.with(requireContext())
                .load(uri)
                .into(binding.accIm)
            Toast.makeText(context, " Glide", Toast.LENGTH_SHORT).show()
        })


        return binding.root

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_GALLERY -> {
                    val selectedImageUri: Uri? = data?.data  //Seçilen resmin urisi alınır

                    /*            if (selectedImageUri != null) {
                                    // Seçilen resmin urisini kullanarak bitmap nesnesi alır.
                                    // Bitmap yaptık çünkü : Resimleri işlemek veya görüntülemek için Android'de Bitmap nesneleri yaygın olarak kullanılır.
                                    val bitmap = MediaStore.Images.Media.getBitmap(
                                        requireContext().contentResolver,
                                        selectedImageUri
                                    )        //Bitmap'e dönüştürdük !

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


                                    binding.accIm.setImageBitmap(resizedBitmap)*/


                    binding.accIm.setImageURI(selectedImageUri)
                    imageUri = selectedImageUri!!
                }
            }
        }
    }
}








//onActivityResult : eski ve geleneksel startActivityForResult ve onActivityResult yöntemleridir
//registerForActivityResult : Android Jetpack ActivityResult API'sini kullanarak daha basit ve düzenli bir şekilde sonuç alışverişi yapmanızı sağlar.




