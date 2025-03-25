package com.example.android_bootcamp.presentation.features.options


import com.example.android_bootcamp.databinding.FragmentOptionsBottomSheetBinding
import com.example.android_bootcamp.presentation.common.BaseBottomSheetFragment

class OptionsBottomSheet : BaseBottomSheetFragment<FragmentOptionsBottomSheetBinding>(FragmentOptionsBottomSheetBinding::inflate) {
    private var imageSelectionListener: ((Boolean) -> Unit)? = null

    fun setOnImageSelectionListener(listener: (Boolean) -> Unit) {
        imageSelectionListener = listener
    }

    override fun setListeners() {
        binding.takePicId.setOnClickListener {
            imageSelectionListener?.invoke(true)
            dismiss()
        }

        binding.uploadId.setOnClickListener {
            imageSelectionListener?.invoke(false)
            dismiss()
        }
    }
}