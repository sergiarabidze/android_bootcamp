package com.example.android_bootcamp.presentation.users

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.android_bootcamp.common.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentUsersBinding
import com.example.android_bootcamp.common.recycler.UsersAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()
    private lateinit var userAdapter: UsersAdapter

    override fun setUp() {
        super.setUp()

        userAdapter = UsersAdapter()
        binding.recyclerId.adapter = userAdapter

        lifecycleScope.launch {
            viewModel.userPagingDataFlow.collectLatest { pagingData ->
                userAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            userAdapter.loadStateFlow.collect { loadStates ->
                val isLoading = loadStates.refresh is LoadState.Loading ||
                        loadStates.append is LoadState.Loading

                val isEndReached = loadStates.refresh is LoadState.NotLoading &&
                        loadStates.append.endOfPaginationReached

                binding.progressBar.isVisible = isLoading && !isEndReached
            }
        }
    }

    override fun setListeners() {
        super.setListeners()
        binding.backArrowId.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
