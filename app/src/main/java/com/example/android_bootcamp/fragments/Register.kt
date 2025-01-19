package com.example.android_bootcamp.fragments

import android.text.InputType
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.android_bootcamp.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentRegisterBinding
import com.example.android_bootcamp.view_models.RegisterViewModel

class Register : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun setListeners() {
        super.setListeners()

        with(binding) {
            passwordVisibilityId.setOnClickListener {
                if (passwordId.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    passwordId.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                } else {
                    passwordId.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                passwordId.text?.let { it1 -> passwordId.setSelection(it1.length) }
            }
            //when we click on the eye icon it will change text type and reveal the password
            registerId.setOnClickListener {
                val email = emailId.text.toString()
                val password = passwordId.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    registerViewModel.registerUser(email, password)//registering new user
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please enter email and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun setUp() {
        super.setUp()
        registerViewModel.registerResponse.observe(viewLifecycleOwner) { response ->
            Toast.makeText(
                requireContext(),
                "Registration successful. User token: ${response.token}",
                Toast.LENGTH_SHORT
            ).show()
        }

        registerViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(
                requireContext(),
                "Registration failed: $errorMessage",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}
