package com.example.android_bootcamp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.android_bootcamp.databinding.FragmentMyOrdersBinding
import com.example.android_bootcamp.viewpager.ViewPagerAdapter


class MyOrders : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerAdapter
    private var _binding: FragmentMyOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.viewPager
        adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter
        updateButtonBackground(0)
        //assigning viewpager2 to adapter

        binding.CompletedBtn.setOnClickListener{
            viewPager.currentItem = 0
        }

        binding.activeBtn.setOnClickListener {
            viewPager.currentItem = 1//set the current item to the second page
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateButtonBackground(position)  //update button background based on the selected page
            }
        })
    }

    private fun updateButtonBackground(position: Int) {
        binding.completedBtnLine.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.button_completed))
        binding.activeButtonLine.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.button_completed))
        binding.activeButtonText.setTextColor(ContextCompat.getColor(requireContext(),R.color.button_completed))
        binding.completedBtnText.setTextColor(ContextCompat.getColor(requireContext(),R.color.button_completed))
        //at first make them default and then change according to which position it is now
        if (position == 0) {
            binding.completedBtnText.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.completedBtnLine.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
        } else {
            binding.activeButtonLine.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.activeButtonText.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
        }
    }
}

