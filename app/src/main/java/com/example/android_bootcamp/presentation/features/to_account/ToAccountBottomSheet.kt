package com.example.android_bootcamp.presentation.features.to_account

import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.R
import com.example.android_bootcamp.databinding.FragmentToAccountBottomSheetBinding
import com.example.android_bootcamp.domain.helper.AccountType
import com.example.android_bootcamp.presentation.common.BaseBottomSheetDialogFragment
import com.example.android_bootcamp.presentation.features.model.AccountPresentation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToAccountBottomSheet :
    BaseBottomSheetDialogFragment<FragmentToAccountBottomSheetBinding>(FragmentToAccountBottomSheetBinding::inflate) {

    private val viewModel: ToAccountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        observeState()
    }

    override fun setListeners() {
        binding.btnSearch.setOnClickListener {
            val inputText = binding.etInput.text.toString()
            val selectedType = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.rbAccountNumber -> AccountType.ACCOUNT_NUMBER
                R.id.rbPersonalId -> AccountType.PERSONAL_ID
                R.id.rbPhoneNumber -> AccountType.PHONE_NUMBER
                else -> null
            }

            if (selectedType == null) {
                binding.etInput.error = "Please select a transfer type"
                return@setOnClickListener
            }

            viewModel.onEvent(ToAccountEvent.ConfirmToAccount(inputText, selectedType))
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.progressBar.isVisible = state.isLoading

                if (state.errorMessage != null) {
                    binding.etInput.error = state.errorMessage
                    viewModel.clearError()
                }

                if (state.isSuccessful) {
                    parentFragmentManager.setFragmentResult(
                        "to_account_result",
                        bundleOf("account" to AccountPresentation())
                    )
                    findNavController().popBackStack()

                    d("result", "result")
                }
            }
        }
    }
}
