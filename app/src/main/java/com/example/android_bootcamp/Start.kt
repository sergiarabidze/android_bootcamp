package com.example.android_bootcamp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_bootcamp.databinding.FragmentStartBinding


class Start : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!//binding property


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeners()//listeners function


    }

    private fun listeners(){
        binding.backArrow.setOnClickListener {
            requireActivity().finish()//if the user clicks on the back arrow, the app will close
        }

        binding.facebookId.setOnClickListener{
            requireContext().showToast("signed in with Facebook")
        }
        binding.googleId.setOnClickListener {
            requireContext().showToast("signed in with Google")
        }
        binding.appleId.setOnClickListener {
            requireContext().showToast("signed in with Apple")
        }
        binding.signUpId.setOnClickListener {
            requireContext().showToast("sign up")
        }//different buttons will show a toast message

        binding.singInButton.setOnClickListener{
            val signUpFragment = CreateAccount()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, signUpFragment)
                .addToBackStack(null)
                .commit()
        }//sign up will take us to the next fragment and this fragment will be added to the back stack

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }


}