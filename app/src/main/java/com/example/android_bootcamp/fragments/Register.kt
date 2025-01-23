package com.example.android_bootcamp.fragments

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentRegisterBinding
import com.example.android_bootcamp.view_models.RegisterViewModel

class Register : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun setListeners() {
        super.setListeners()

        with(binding) {
            registerId.setOnClickListener {
                val email = emailId.text.toString()
                val password = passwordId.text.toString()
                val repeatPassword = passwordRepId.text.toString()

                if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill in all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password != repeatPassword) {
                    Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    registerViewModel.registerUser(email, password)
                }
            }

            backArrowId.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun setUp() {
        super.setUp()

        registerViewModel.registerResponse.observe(viewLifecycleOwner) {
            val email = binding.emailId.text.toString()
            val password = binding.passwordId.text.toString()

            Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()

            setFragmentResult(
                "REGISTER_RESULT",
                bundleOf("email" to email, "password" to password)
            )//sent back email and password to login fragment

            findNavController().popBackStack()
        }

        registerViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(
                requireContext(),
                "Registration failed: $errorMessage",
                Toast.LENGTH_SHORT
            ).show()
        }//error message if registration fails
    }
}
