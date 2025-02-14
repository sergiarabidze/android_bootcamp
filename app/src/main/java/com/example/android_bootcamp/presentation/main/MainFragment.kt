package com.example.android_bootcamp.presentation.main

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.example.android_bootcamp.R
import com.example.android_bootcamp.common.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment :
    BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {

    private val args: MainFragmentArgs by navArgs()
    private val mainViewModel: MainViewModel by viewModels()

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            emailId.text = args.email

            logOutId.setOnClickListener {
                mainViewModel.clearSession { navigateToLogin() }
            }
            usersBtnId.setOnClickListener {
                findNavController().navigate(R.id.usersFragment)
            }
        }
    }

    private fun navigateToLogin() {
        val navController = findNavController()
        val navOptions = navOptions {
            popUpTo(R.id.mainFragment) {
                inclusive = true
            }
        }
        navController.navigate(R.id.login, null, navOptions)
    }
}
