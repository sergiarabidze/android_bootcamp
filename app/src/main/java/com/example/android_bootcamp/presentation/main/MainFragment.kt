package com.example.android_bootcamp.presentation.main


import androidx.fragment.app.viewModels
import com.example.android_bootcamp.common.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {
     private val viewModel:MainViewModel by viewModels()
     override fun setUp() {
          super.setUp()

     }

}