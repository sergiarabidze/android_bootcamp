package com.example.android_bootcamp.presentation.login

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.R
import com.example.android_bootcamp.common.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentLoginBinding
import com.example.android_bootcamp.remote.httpRequest.Resource
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch

@AndroidEntryPoint
class Login : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun setUp() {
        super.setUp()
        parentFragmentManager.setFragmentResultListener(
            "REGISTER_RESULT",
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            binding.emailId.setText(email)
            binding.passwordId.setText(password)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.loginState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.login_successful, resource.data.token),
                            Toast.LENGTH_SHORT
                        ).show()
                        if (binding.checkbox.isChecked) {
                            loginViewModel.saveSession(resource.data.token, binding.emailId.text.toString())
                        }
                        navigateToHome(binding.emailId.text.toString())
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                    }

                    Resource.Idle -> {
                        //default
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.readSession().collect { userData ->
                val (savedToken, savedEmail) = userData
                if (!savedToken.isNullOrEmpty() && !savedEmail.isNullOrEmpty()) {
                    navigateToHome(savedEmail)
                }
            }
        }
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            registerId.setOnClickListener {
                findNavController().navigate(LoginDirections.actionLoginToRegister())
            }

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val email = emailId.text.toString()
                    val password = passwordId.text.toString()
                    loginId.isEnabled = email.isValidEmail() && password.isNotEmpty()
                }

                override fun afterTextChanged(s: Editable?) {}
            }
            emailId.addTextChangedListener(textWatcher)
            passwordId.addTextChangedListener(textWatcher)
            loginId.setOnClickListener {
                val email = emailId.text.toString()
                val password = passwordId.text.toString()
                loginViewModel.loginUser(email, password,binding.checkbox.isChecked)
            }
        }
    }

    private fun navigateToHome(email: String) {
        val action = LoginDirections.actionLoginToMainFragment(email)
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.login, true)
            .build()
        findNavController().navigate(action, navOptions)
    }

    private fun String.isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}
