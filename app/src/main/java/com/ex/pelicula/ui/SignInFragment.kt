package com.ex.pelicula.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentSignInBinding
import com.ex.pelicula.viewModel.SignInViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignInFragment : Fragment() {


    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.GONE



        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.lifecycleOwner = viewLifecycleOwner



        binding.button4.setOnClickListener {

            var email = binding.txtInEmail.text.toString()
            var password = binding.txtInPassword.text.toString()

            // GlobalScope
            lifecycleScope.launch {
                viewModel.signIn(email.toString(), password.toString())
            }
        }


        viewModel.res.observe(viewLifecycleOwner, Observer { isValid ->

            if (isValid) {
                Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.constraint, HomePageFragment())
                    .commit()

                // Anasayfaya yönlendir.

            } else Toast.makeText(requireContext(), "Unsuccessful", Toast.LENGTH_SHORT).show()

        })


        binding.signUp.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.constraint, SignUpFragment())
                .commit()
        }


        return binding.root
    }
}


/*
class SignInViewModelFactory(private val repo: SignInRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }*/


//     viewModel = ViewModelProvider(requireActivity()).get(SignInViewModel::class.java)
//Doğrudan SignInViewModel sınıfından alıyor
//SignInViewModel bağımlılığı var ( Repo ) bu yüzden bunu kullanamazsın !! Factory kullanabilirsin

//     val repository = Repository()
//       viewModel = ViewModelProvider(this, SignInViewModelFactory(repository)).get(SignInViewModel::class.java)
//SıgnIn'den viewModel oluşturmak için SıgnRepo'yu da kullanmamız gerekiyor
// Hilt kullanmasaydın bunu kullanacaktın.





