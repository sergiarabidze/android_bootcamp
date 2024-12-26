package com.example.android_bootcamp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_bootcamp.databinding.FragmentAddLocationBinding
import com.example.android_bootcamp.databinding.FragmentUpdateBinding

class Update : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private var itemId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            itemId = it.getInt("item_id")
        }

        binding.updInfoId.setOnClickListener {
            val location = binding.updLocationInfoId.text.toString()
            val address = binding.updWhereToSend.text.toString()

            val resultBundle = Bundle().apply {
                putInt("item_id", itemId)
                putString("location_info", location)
                putString("address_info", address)
            }
            parentFragmentManager.setFragmentResult("updateResult", resultBundle)
            parentFragmentManager.popBackStack()
            //same logic here but we add id also because we need id to identify which item we want to update
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
