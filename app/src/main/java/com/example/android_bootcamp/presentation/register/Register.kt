package com.example.android_bootcamp.presentation.register

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.BuildConfig
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentRegisterBinding
import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.model.ValidationResult
import com.example.android_bootcamp.presentation.helper.launchCoroutine
import com.example.android_bootcamp.presentation.helper.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Register : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()
    override fun setUp() {
        if (BuildConfig.DEBUG){
            binding.emailId.setText("eve.holt@reqres.in")
            binding.passwordId.setText("paroli")
            binding.passwordRepId.setText("paroli")
        }
    }

    override fun setListeners() {
        with(binding) {
            registerId.setOnClickListener {
                val email = emailId.text.toString()
                val password = passwordId.text.toString()
                val confirmPassword = passwordRepId.text.toString()
                viewModel.onEvent(RegisterEvent.Submit(email, password, confirmPassword))
            }

            backArrowId.setOnClickListener {
                findNavController().popBackStack()
            }

        }
    }

    override fun setObservers() {

        launchCoroutine {
            viewModel.uiState.collectLatest { state ->
                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            }
        }

        launchCoroutine {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is RegisterUiEvent.NavigateToHome -> {
                        showToast(getString(R.string.registration_successful))
                        navigate()
                    }

                    is RegisterUiEvent.ShowError -> {
                        showToast(event.message)
                    }
                    else -> {}
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


