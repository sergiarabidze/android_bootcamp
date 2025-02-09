package com.example.android_bootcamp.presentation.main

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.example.android_bootcamp.R
import com.example.android_bootcamp.common.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.local.datastore.dataStore
import kotlinx.coroutines.launch

class MainFragment :
    BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {

    private val args: MainFragmentArgs by navArgs()

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            emailId.text = args.email

            logOutId.setOnClickListener {
                clearSession()
            }
            usersBtnId.setOnClickListener {
                findNavController().navigate(R.id.usersFragment)
            }
        }
    }

    private fun clearSession() {
       val job =  lifecycleScope.launch {
            requireContext().dataStore.edit { preferences ->
                preferences.clear()
            }
        }
        job.invokeOnCompletion {
            navigateToLogin()
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
