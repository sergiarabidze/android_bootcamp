package com.example.android_bootcamp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_bootcamp.databinding.FragmentAddLocationBinding

class AddLocation : Fragment() {


    private var _binding: FragmentAddLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddLocationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addInfoId.setOnClickListener {
            val location = binding.locationInfoId.text.toString()
            val address = binding.whereToSend.text.toString()
            val resultBundle = Bundle()//we make bundle
            resultBundle.putString("location_info", location)
            resultBundle.putString("address_info", address)//then we put information in the bundle
            parentFragmentManager.setFragmentResult("locationResult", resultBundle)//set the result
            parentFragmentManager.popBackStack()//kill the fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
