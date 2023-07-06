package com.ex.pelicula.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ex.pelicula.R
import com.ex.pelicula.viewModel.SignUpViewModel
import com.ex.pelicula.databinding.FragmentSignUpBinding
import com.ex.pelicula.viewModel.SignInViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.GONE



        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)

          binding.user = viewModel // user değişkeni vm'ye atandı
        binding.lifecycleOwner = this //Lifecycle'ı doğru şekilde takp edecek


        binding.button4.setOnClickListener {
            var email = binding.txtEmail.text.toString()
            var password = binding.txtPass.text.toString()
            var name = binding.txtName.text.toString()
            var lastName = binding.txtLastName.text.toString()


            /*            viewModel.emailMLD.value = email
                        viewModel.passwordMLD.value = password
                        viewModel.nameMLD.value = name
                        viewModel.lastNameMLD.value = lastName*/

            // XML'den zaten view modele gidiyor ( @=user.email .... ) . Buna gerek var mı ?

            //  viewModel.signUp()

            lifecycleScope.launch {
            viewModel.signUp(email, password, name, lastName)

        }}





        viewModel.signUpRes.observe(this, Observer { isValid ->
            if (isValid) {
                Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.constraint, SignInFragment())
                    .commit()
            } else Toast.makeText(requireContext(), "Unsuccessful", Toast.LENGTH_SHORT).show()

        })
        /*     viewModel.signUpRes.observe(this, Observer {
                 binding.txtInPassword.text = it.toString()
             })
             viewModel.signUpRes.observe(this, Observer {
                 binding.txtInEmail.text = it.toString()
             })
             viewModel.signUpRes.observe(this, Observer {
                 binding.txtLastName.text = it.toString()
             })
             // Değer güncellendiğinde alır txtLastName'e atar
     */













        return binding.root


    }
}
