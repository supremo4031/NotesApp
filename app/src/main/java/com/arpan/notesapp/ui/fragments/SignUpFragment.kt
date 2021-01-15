package com.arpan.notesapp.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.arpan.notesapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.custom_progressbar.*
import kotlinx.android.synthetic.main.fragment_signup.*
import java.lang.Exception

class SignUpFragment : Fragment(R.layout.fragment_signup) {

    private val firebaseAuth = Firebase.auth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sign_up_button.setOnClickListener {
            val name = sign_up_name.text.toString()
            val email = sign_up_email.text.toString()
            val password = sign_up_password.text.toString()
            val confirmPassword = sign_up_confirm_password.text.toString()

            when {
                name.isEmpty() -> {
                    sign_up_name.error = "Name cannot be empty"
                }
                email.isEmpty() -> {
                    sign_up_email.error = "Email cannot be empty"
                }
                password.isEmpty() -> {
                    sign_up_password.error = "Password cannot be empty"
                }
                confirmPassword.isEmpty() -> {
                    sign_up_confirm_password.error = "Confirm password cannot be empty"
                }
                password != confirmPassword -> {
                    sign_up_confirm_password.error = "Password and confirm password should be same"
                }
                else -> {
                    val dialog = showProgressDialog(
                        requireContext()
                    )
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.signUpFragment, true)
                            .build()
                        findNavController().navigate(
                            R.id.action_signUpFragment_to_mainFragment,
                            null,
                            navOptions
                        )
                        dialog.dismiss()
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), "Cannot create user", Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }
                }
            }
        }
    }



    private fun getAlertDialog(
        context: Context
    ): AlertDialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val customLayout: View =
            layoutInflater.inflate(R.layout.custom_progressbar, null)
        builder.setView(customLayout)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    private fun showProgressDialog(context: Context): AlertDialog {
        val dialog = getAlertDialog(context)
        dialog.show()
        dialog.text_progress_bar.text = "Loading, Please wait..."
        return dialog
    }

}