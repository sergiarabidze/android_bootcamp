package com.example.android_bootcamp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.android_bootcamp.databinding.FragmentRatingBinding
import com.example.android_bootcamp.recycler.ItemModel


class Rating : BottomSheetDialogFragment() {

    private var _binding: FragmentRatingBinding? = null
    private val binding get() = _binding!!
    private var item: ItemModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item = arguments?.getParcelable("item_model")
//we get item

        item?.let {
            binding.photoId.setImageResource(it.imageResId)
            binding.titleId.text = it.name

            when (it.color) {
                "red" -> binding.colorId.setBackgroundResource(R.drawable.red_custom)
                "black" -> binding.colorId.setBackgroundResource(R.drawable.black_custom)
            }

            binding.colorQuantityId.text = getString(R.string.qty, it.color, it.quantity.toString())
            binding.priceId.text = getString(R.string.pricee, it.price.toString())

            binding.statusId.text = it.status
        }//creating item model in our fragment

        binding.submitButton.setOnClickListener {
            Toast.makeText(requireContext(), "Rating submitted", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

}

