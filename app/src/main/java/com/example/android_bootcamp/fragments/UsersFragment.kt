package com.example.android_bootcamp.fragments


import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.api.User
import com.example.android_bootcamp.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentUsersBinding
import com.example.android_bootcamp.recycler.UsersAdapter
import com.example.android_bootcamp.resource.Resource
import com.example.android_bootcamp.view_models.UsersViewModel
import kotlinx.coroutines.launch

class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {
    private val registerViewModel: UsersViewModel by viewModels()

    override fun setUp() {
        super.setUp()

        viewLifecycleOwner.lifecycleScope.launch {
            registerViewModel.usersResponse.collect { usersResponse ->
                when(usersResponse){
                    is Resource.Loading ->
                        binding.progressBar.visibility = View.VISIBLE

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, usersResponse.message, Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Success ->{
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerId.visibility = View.VISIBLE
                        val users = usersResponse.data.data
                        val adapter = UsersAdapter(users)
                        binding.recyclerId.adapter = adapter
                    }

                    Resource.Idle -> {
                        //default
                    }
                }
            }
        }
        registerViewModel.fetchUsers(page = 1)
    }

    override fun setListeners() {
        super.setListeners()
        binding.backArrowId.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}