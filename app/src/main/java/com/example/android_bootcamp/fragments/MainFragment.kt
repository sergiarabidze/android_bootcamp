package com.example.android_bootcamp.fragments


import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding


class MainFragment : BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate){
    override fun setListeners() {
        super.setListeners()
        with(binding){
            registerId.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragment2ToRegister()
                findNavController().navigate(action)
            }
            loginId.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragment2ToLogin2()
                findNavController().navigate(action)
            }
        }
    }
}