package com.example.android_bootcamp.presentation.login

import android.text.Editable
import android.text.TextWatcher
import android.util.Log.d
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentLoginBinding
import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.model.ValidationResult
import com.example.android_bootcamp.presentation.helper.launchCoroutine
import com.example.android_bootcamp.presentation.helper.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

import kotlinx.coroutines.launch

@AndroidEntryPoint
class Login : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun setUp() {
        parentFragmentManager.setFragmentResultListener(
            "REGISTER_RESULT",
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            binding.emailId.setText(email)
            binding.passwordId.setText(password)
        }
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            registerId.setOnClickListener {
                findNavController().navigate(LoginDirections.actionLoginToRegister())
            }

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val email = emailId.text.toString()
                    val password = passwordId.text.toString()
                    loginViewModel.validateFields(email, password)
                }

                override fun afterTextChanged(s: Editable?) {}
            }

            emailId.addTextChangedListener(textWatcher)
            passwordId.addTextChangedListener(textWatcher)

            loginId.setOnClickListener {
                val email = emailId.text.toString()
                val password = passwordId.text.toString()
                val rememberMe = binding.checkbox.isChecked
                loginViewModel.onEvent(LoginEvent.Submit(email, password, rememberMe))
            }
        }
    }

    override fun setObservers() {
        launchCoroutine {
            loginViewModel.state.collectLatest { state ->
                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                binding.loginId.isActivated = state.isButtonEnabled
            }

        }
        launchCoroutine {
            loginViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is LoginUiEvent.NavigateToHome -> {
                        navigateToHome(event.email)
                    }
                    is LoginUiEvent.ShowError -> {
                        showToast(event.message)
                    }
                }
            }
        }
    }

    private fun navigateToHome(email: String) {
        val action = LoginDirections.actionLoginToMainFragment(email)
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.login, true)
            .build()
        findNavController().navigate(action, navOptions)
        findNavController().popBackStack()
    }

}
