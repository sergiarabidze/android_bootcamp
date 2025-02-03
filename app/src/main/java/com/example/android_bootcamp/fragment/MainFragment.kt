package com.example.android_bootcamp.fragment

import UserAdapter
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.android_bootcamp.BaseFragment
import com.example.android_bootcamp.R
import com.example.android_bootcamp.api.RetrofitInstance
import com.example.android_bootcamp.data.UserDataBase
import com.example.android_bootcamp.data.UserRepository
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.extensions.isNetworkAvailable
import com.example.android_bootcamp.view_model.UserViewModel
import com.example.android_bootcamp.view_model.UserViewModelFactory
import kotlinx.coroutines.launch


class MainFragment : BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {

    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(UserDataBase.getDatabase(requireContext()).userDao(),RetrofitInstance.api))
    }

    private lateinit var userAdapter: UserAdapter


    override fun setUp() {
        super.setUp()
        //addUsers()
        setupRecyclerView()
        observeUsers()
        observeLoadingState()
        observeErrorMessage()
        if (isNetworkAvailable(requireContext())){
            Toast.makeText(requireContext(), getString(R.string.you_are_online), Toast.LENGTH_SHORT).show()
            viewModel.fetchUsers()
        }else{
            Toast.makeText(requireContext(), getString(R.string.you_are_offline), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.recyclerView.apply {
            adapter = userAdapter
        }
    }

    private fun observeUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.users.collect { users ->
                userAdapter.submitList(users)
            }
        }
    }

    private fun observeLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun observeErrorMessage() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessage.collect { errorMessage ->
                if (errorMessage != null) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}