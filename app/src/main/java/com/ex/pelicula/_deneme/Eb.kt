package com.ex.pelicula._deneme

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ex.pelicula.R
import com.google.firebase.auth.FirebaseAuth


class eb : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()

        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        var x = view.findViewById<EditText>(R.id.txt_in_email)
        var y = view.findViewById<EditText>(R.id.txt_in_password)
        var button = view.findViewById<Button>(R.id.button4)

        button.setOnClickListener {

            auth.signInWithEmailAndPassword(x.text.toString(), y.text.toString())
                .addOnCompleteListener (requireActivity()){ task ->
                    if (task.isSuccessful) Toast.makeText(
                        requireContext(),
                        "Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    else Toast.makeText(requireContext(), "Unsuccessful", Toast.LENGTH_SHORT).show()
                }
        }


        return view
    }

}