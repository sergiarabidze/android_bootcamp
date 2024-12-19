package com.example.android_bootcamp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.android_bootcamp.databinding.FragmentBlankBinding


class CreateAccount : Fragment() {
    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    private fun listeners() {
        binding.facebookFrameId.setOnClickListener {
            requireContext().showToast("continuing with facebook")
        }

        binding.googleFrameId.setOnClickListener {
            requireContext().showToast("continuing with google")
        }

        binding.appleFrameId.setOnClickListener {
            requireContext().showToast("continuing with apple")
        }

        binding.signUpId.setOnClickListener {
            requireContext().showToast("sign up")
        }

        binding.singUpButton.setOnClickListener {
            if (binding.emailId.text.toString().isEmpty() || binding.passwordId.text.toString().isEmpty()) {
                requireContext().showToast("please fill all the fields")
            } else {
                requireContext().showToast("account created successfully")
            }
        }

        binding.backArrowFragment.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }//it will pop current fragment from the backstack and take us back to the previous fragment


        emailText()
        passwordText()
        passwordVisibility()
    }

    private fun emailText() {
        binding.emailId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}


            @SuppressLint("UseCompatTextViewDrawableApis")
            override fun afterTextChanged(s: Editable?) {
                // Check if there is any text in the EditText
                if (s.isNullOrEmpty()) {
                    // If no text, change the icon color to gray
                    binding.emailId.setCompoundDrawableTintList(
                        ContextCompat.getColorStateList(requireContext(), R.color.gray)
                    )
                } else {
                    // If there's text, change the icon color to black
                    binding.emailId.setCompoundDrawableTintList(
                        ContextCompat.getColorStateList(requireContext(), R.color.black)
                    )
                }
            }


        })

    }

    private fun passwordText() {
        binding.passwordId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}


            @SuppressLint("UseCompatTextViewDrawableApis")
            override fun afterTextChanged(s: Editable?) {
                // Check if there is any text in the EditText
                if (s.isNullOrEmpty()) {
                    // If no text, change the icon color to gray
                    binding.passwordId.setCompoundDrawableTintList(
                        ContextCompat.getColorStateList(requireContext(), R.color.gray)
                    )
                } else {
                    // If there's text, change the icon color to black
                    binding.passwordId.setCompoundDrawableTintList(
                        ContextCompat.getColorStateList(requireContext(), R.color.black)
                    )
                }
            }
        })
    }

    private fun passwordVisibility() {
        binding.eyeId.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Show password as plain text
                binding.passwordId.transformationMethod = null
            } else {
                // Mask password
                binding.passwordId.transformationMethod =
                    android.text.method.PasswordTransformationMethod.getInstance()
            }

            // Ensure the cursor stays at the end
            binding.passwordId.setSelection(binding.passwordId.text!!.length)
        }

    }//I tried immediately changing input type but it did not worked so here we use transformation method
}