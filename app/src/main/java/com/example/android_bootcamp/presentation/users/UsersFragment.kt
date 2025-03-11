package com.example.android_bootcamp.presentation.users

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.android_bootcamp.presentation.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentUsersBinding
import com.example.android_bootcamp.presentation.common.recycler.UsersAdapter
import com.example.android_bootcamp.presentation.helper.NetworkUtils
import com.example.android_bootcamp.presentation.helper.launchCoroutine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()
    private lateinit var userAdapter: UsersAdapter

    override fun setUp() {

        if (NetworkUtils.isInternetAvailable(context = requireContext())){
            Toast.makeText(requireContext(),"you are online",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(),"you are offline",Toast.LENGTH_SHORT).show()
        }
    }

    override fun setRecycler() {
        userAdapter = UsersAdapter()
        binding.recyclerId.adapter = userAdapter
    }


    override fun setListeners() {
        binding.backArrowId.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setObservers() {
        launchCoroutine(Dispatchers.IO) {
            viewModel.userPagingDataFlow.collectLatest { pagingData ->
                userAdapter.submitData(pagingData)
            }
        }

        launchCoroutine {
            userAdapter.loadStateFlow.collect { loadStates ->
                val isLoading = loadStates.refresh is LoadState.Loading ||
                        loadStates.append is LoadState.Loading

                val isEndReached = loadStates.refresh is LoadState.NotLoading &&
                        loadStates.append.endOfPaginationReached

                binding.progressBar.isVisible = isLoading && !isEndReached
            }
        }
    }

}