package com.ex.pelicula.ui

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
import com.ex.pelicula.R
import com.ex.pelicula.databinding.FragmentAccountBinding
import com.ex.pelicula.viewModel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AccountFragment : Fragment() {


    private val viewModel: AccountViewModel by viewModels()
    private lateinit var binding: FragmentAccountBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        binding.lifecycleOwner = this

        viewModel.getUser()

        viewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer { user ->
            if (user !== null) {
                Toast.makeText(context, "Not null", Toast.LENGTH_SHORT).show()

                Log.d("userId-user[0]", user[0])
                Log.d("userId-user[1]", user[1])

                binding.accEmail.text = user[0]
                binding.accName.text = user[1]

            }
            else Toast.makeText(context, " Null", Toast.LENGTH_SHORT).show()
        })



        return binding.root
    }


}