package com.example.android_bootcamp.presentation.features.bottomsheet


import androidx.navigation.fragment.navArgs
import com.example.android_bootcamp.R
import com.example.android_bootcamp.common.BaseBottomSheet
import com.example.android_bootcamp.databinding.FragmentLocationDetailsBinding

class LocationDetails : BaseBottomSheet<FragmentLocationDetailsBinding>(FragmentLocationDetailsBinding::inflate) {
    private val args : LocationDetailsArgs by navArgs()
    override fun setUp() {
        super.setUp()
        binding.Title.text = getString(R.string.title_text, args.title)
        binding.Address.text = getString(R.string.address_text, args.address)
    }
}
