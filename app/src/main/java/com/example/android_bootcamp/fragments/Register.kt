package com.example.android_bootcamp.fragments

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.R
import com.example.android_bootcamp.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentRegisterBinding
import com.example.android_bootcamp.resource.Resource
import com.example.android_bootcamp.view_models.RegisterViewModel
import kotlinx.coroutines.launch

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
                        getString(R.string.please_fill_in_all_fields),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password != repeatPassword) {
                    Toast.makeText(requireContext(),
                        getString(R.string.passwords_do_not_match), Toast.LENGTH_SHORT)
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

        viewLifecycleOwner.lifecycleScope.launch {
            registerViewModel.registerState.collect { registerState ->
                when (registerState) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.registration_successful),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val email = binding.emailId.text.toString()
                        val password = binding.passwordId.text.toString()
                        setFragmentResult(
                            "REGISTER_RESULT",
                            bundleOf("email" to email, "password" to password)
                        )//sent back email and password to login fragment

                        findNavController().popBackStack()
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.registration_failed, registerState.message), Toast.LENGTH_SHORT
                        ).show()
                    }

                    Resource.Idle -> {
                        //default
                    }
                }
            }
        }
    }
}

