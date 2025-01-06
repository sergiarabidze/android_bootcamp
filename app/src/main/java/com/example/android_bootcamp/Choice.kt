package com.example.android_bootcamp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android_bootcamp.databinding.FragmentChoiceBinding

class Choice : Fragment() {
    private var _binding: FragmentChoiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
    }

    private fun listeners(){
        val editText = binding.sizeEditText
        binding.enterBtn.setOnClickListener {
            if (editText.text.toString().isEmpty()||editText.text.toString().toInt()<3||editText.text.toString().toInt()>5) {
                Toast.makeText(context, "Enter number from 3 to 5", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, Game.newInstance(editText.text.toString()))
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}