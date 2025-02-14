package com.example.android_bootcamp.presentation.main


import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.android_bootcamp.common.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.helper.Resource
import com.example.android_bootcamp.helper.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class MainFragment :
    BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {
    private val viewModel: MainViewModel by viewModels()
    private val adapter = ViewPagerAdapter()
    override fun setUp() {
        super.setUp()
        setUpPager()
        observe()
    }

    private fun setUpPager() {
        with(binding) {
            pagerTwoId.adapter = adapter
            pagerTwoId.offscreenPageLimit = 3

            pagerTwoId.setPageTransformer { page, position ->
                val scaleFactor = 0.85f + (1 - abs(position)) * 0.15f
                page.scaleY = scaleFactor
                page.alpha = 0.5f + (1 - abs(position)) * 0.5f
                val pageMarginPx = 40

                when {
                    position < -1 -> {
                        page.translationX = -page.width.toFloat()
                    }

                    position <= 1 -> {
                        val offset = position * pageMarginPx
                        page.translationX = offset
                    }

                    else -> {
                        page.translationX = page.width.toFloat()
                    }
                }
            }
        }
    }

    private fun observe() {
        viewModel.getPlaces()
        lifecycleScope.launch {
            binding.progressBarId.visibility = View.VISIBLE
            viewModel.places.collectLatest { response ->
                when (response) {
                    is Resource.Success -> {
                        binding.progressBarId.visibility = View.GONE
                        adapter.submitList(response.data)
                    }

                    is Resource.Error -> {
                        binding.progressBarId.visibility = View.GONE
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    is Resource.Loading -> {
                        binding.progressBarId.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

}