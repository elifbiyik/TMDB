package com.ex.pelicula.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentSignInBinding
import com.ex.pelicula.viewModel.SignInViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignInFragment : Fragment() {


    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    lateinit var bottomNav: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bottomNav = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.button4.setOnClickListener {
            var email = binding.txtInEmail.text.toString()
            var password = binding.txtInPassword.text.toString()

            lifecycleScope.launch {
                viewModel.signIn(email, password)
            }
        }

        viewModel.res.observe(viewLifecycleOwner, Observer { isValid ->
            if (isValid) {
                Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.constraint, HomePageFragment())
                    .addToBackStack(null)
                    .commit()
            } else Toast.makeText(requireContext(), "Unsuccessful", Toast.LENGTH_SHORT).show()

        })

        binding.signUp.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.constraint, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
        return binding.root
    }
}





