package com.example.android_bootcamp.fragments

import android.content.Context
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android_bootcamp.R
import com.example.android_bootcamp.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding

class MainFragment :
    BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {

    private val args: MainFragmentArgs by navArgs()

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            emailId.text = args.email

            logOutId.setOnClickListener {
                clearSessionAndNavigateToLogin()
            }
        }
    }

    private fun clearSessionAndNavigateToLogin() {
        val sharedPreferences =
            requireContext().getSharedPreferences("SessionPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()//we clear session

        findNavController().navigate(MainFragmentDirections.actionMainFragmentToLogin())
        findNavController().popBackStack(
            R.id.login,
            false
        )//pop this fragment from backstack and go to login fragment
    }
}
