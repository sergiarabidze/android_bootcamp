package com.example.android_bootcamp.presentation.register

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentRegisterBinding
import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.model.ValidationResult
import com.example.android_bootcamp.presentation.helper.launchCoroutine
import com.example.android_bootcamp.presentation.helper.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Register : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    override fun setListeners() {
        super.setListeners()

        with(binding) {
            registerId.setOnClickListener {
                onRegisterClicked()
            }

            backArrowId.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun onRegisterClicked() {
        val email = binding.emailId.text.toString()
        val password = binding.passwordId.text.toString()
        val confirmPassword = binding.passwordRepId.text.toString()
        viewModel.validateCredentials(email, password, confirmPassword)
    }

    override fun setObservers() {
        launchCoroutine {
            viewModel.validationState.collect { validationResult ->
                when (validationResult) {
                    is ValidationResult.Success -> {
                    }
                    is ValidationResult.Error -> {
                        showToast(validationResult.message)
                    }
                }
            }
        }

        launchCoroutine {
            viewModel.registerState.collect { registerState ->
                when (registerState) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        showToast(
                            getString(R.string.registration_successful)
                        )
                        navigate()
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showToast(getString(R.string.registration_failed, registerState.message))

                    }
                    Resource.Idle -> {
                    }
                }
            }
        }


    }


    private fun navigate() {
        val email = binding.emailId.text.toString()
        val password = binding.passwordId.text.toString()
        setFragmentResult(
            "REGISTER_RESULT",
            bundleOf("email" to email, "password" to password)
        )
        findNavController().popBackStack()
    }
}

