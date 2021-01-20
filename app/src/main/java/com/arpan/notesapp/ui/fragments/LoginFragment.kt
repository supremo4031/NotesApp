package com.arpan.notesapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.arpan.notesapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {


    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val user = firebaseAuth.currentUser

        if(user != null) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_loginFragment_to_mainFragment,
                savedInstanceState,
                navOptions
            )
        }

        login_button.setOnClickListener {
            val email = login_email.text.toString()
            val password = login_password.text.toString()

            when {
                email.isEmpty() -> {
                    login_email.error = "Email cannot be empty"
                }
                password.isEmpty() -> {
                    login_password.error = "Password cannot be empty"
                }
                else -> {
                    login_progressBar.visibility = View.VISIBLE
                    login_button.visibility = View.GONE
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.loginFragment, true)
                            .build()
                        findNavController().navigate(
                            R.id.action_loginFragment_to_mainFragment,
                            savedInstanceState,
                            navOptions
                        )
                    }.addOnFailureListener {
                        login_progressBar.visibility = View.INVISIBLE
                        login_button.visibility = View.VISIBLE
                    }
                }
            }
        }

        login_sign_up_text.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_signUpFragment
            )
        }
    }


}