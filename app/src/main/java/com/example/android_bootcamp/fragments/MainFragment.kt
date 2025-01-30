package com.example.android_bootcamp.fragments


import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.android_bootcamp.R
import com.example.android_bootcamp.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.extension_function.isValidEmail
import com.example.android_bootcamp.extension_function.showToast
import com.example.android_bootcamp.viewmodel.UserPreferencesViewModel
import com.example.android_bootcamp.viewmodel.UserPreferencesViewModelFactory
import kotlinx.coroutines.launch


class MainFragment :
    BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {

    private lateinit var viewModel: UserPreferencesViewModel

    override fun setUp() {
        super.setUp()
        viewModel = ViewModelProvider(
            this,
            UserPreferencesViewModelFactory(requireContext())
        )[UserPreferencesViewModel::class.java]
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            saveButton.setOnClickListener {
                val firstName = firstName.text.toString()
                val lastName = lastName.text.toString()
                val email = email.text.toString()
                if (!email.isValidEmail()) {
                    requireContext().showToast(getString(R.string.invalid_email))
                    return@setOnClickListener
                }
                if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()) {
                    viewModel.saveUserPreferences(firstName, lastName, email)
                    requireContext().showToast(getString(R.string.data_saved_successfully))
                    infoId.visibility = View.INVISIBLE
                    binding.firstName.text?.clear()
                    binding.lastName.text?.clear()
                    binding.email.text?.clear()
                } else {
                    requireContext().showToast(getString(R.string.please_fill_all_fields))
                }
            }
            readButton.setOnClickListener {

                lifecycleScope.launch {
                    viewModel.userPreferencesFlow.collect { userPreferences ->
                        infoId.text = getString(
                            R.string.fullname_email,
                            userPreferences.firstName,
                            userPreferences.lastName,
                            userPreferences.email
                        )
                        requireContext().showToast(getString(R.string.data_loaded))
                    }
                }
                infoId.visibility = View.VISIBLE
            }
        }
    }
}

