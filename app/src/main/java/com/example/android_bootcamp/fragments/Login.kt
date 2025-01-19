package com.example.android_bootcamp.fragments

import android.text.InputType
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.android_bootcamp.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentLoginBinding
import com.example.android_bootcamp.view_models.LoginViewModel


class Login : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

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
            }//when we click on the eye icon it will change text type and reveal the password

            loginId.setOnClickListener {
                val email = "eve.holt@reqres.in"//we can log in with only one email
                val username = userNameId.text.toString()
                val password = passwordId.text.toString()
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    loginViewModel.loginUser(email, password)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please enter username and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun setUp() {
        super.setUp()
        loginViewModel.loginResponse.observe(viewLifecycleOwner) { response ->
            Toast.makeText(
                requireContext(),
                "Login successful. user token: ${response.token}",
                Toast.LENGTH_SHORT
            ).show()
        }//we observe variables and show the result

        loginViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), "Login failed: $errorMessage", Toast.LENGTH_SHORT)
                .show()
        }//error message

    }


}