package com.example.android_bootcamp.presentation.features.accounts

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bootcamp.databinding.FragmentAccountBottomSheetBinding
import com.example.android_bootcamp.presentation.common.BaseBottomSheetDialogFragment
import com.example.android_bootcamp.presentation.exstension.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AccountBottomSheet : BaseBottomSheetDialogFragment<FragmentAccountBottomSheetBinding>(
    FragmentAccountBottomSheetBinding::inflate
) {

    private val viewModel: AccountsViewModel by viewModels()

    private lateinit var accountsAdapter: AccountsAdapter

    override fun setUp() {
        accountsAdapter = AccountsAdapter { selectedAccount ->
            parentFragmentManager.setFragmentResult(
                "account_request",
                bundleOf("selected_account" to selectedAccount)
            )
            findNavController().popBackStack()
        }
        binding.rvAccounts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = accountsAdapter
        }
    }

    override fun setRecycler() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.accounts.collect { resource ->
                binding.progressId.isVisible = resource.isLoading
                binding.rvAccounts.isVisible = !resource.isLoading

                accountsAdapter.submitList(resource.accounts)
                if (resource.error != null) {
                    showSnackbar(resource.error)
                    viewModel.clearError()
                }
            }
        }
    }
}
