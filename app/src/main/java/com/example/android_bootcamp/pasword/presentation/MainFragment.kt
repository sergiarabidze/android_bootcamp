package com.example.android_bootcamp.pasword.presentation


import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bootcamp.base.BaseFragment
import com.example.android_bootcamp.R
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment :
    BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {

    private val passcodeViewModel: PasscodeViewModel by viewModels()

    override fun setUp() {
        super.setUp()
        setupKeypad()
        observePasscodeResult()
    }


    private fun setupKeypad() {
        binding.button1.setOnClickListener {
            passcodeViewModel.addDigit("1")
        }
        binding.button2.setOnClickListener {
            passcodeViewModel.addDigit("2")
        }
        binding.button3.setOnClickListener {
            passcodeViewModel.addDigit("3")
        }
        binding.button4.setOnClickListener {
            passcodeViewModel.addDigit("4")
        }
        binding.button5.setOnClickListener {
            passcodeViewModel.addDigit("5")
        }
        binding.button6.setOnClickListener {
            passcodeViewModel.addDigit("6")
        }
        binding.button7.setOnClickListener {
            passcodeViewModel.addDigit("7")
        }
        binding.button8.setOnClickListener {
            passcodeViewModel.addDigit("8")
        }
        binding.button9.setOnClickListener {
            passcodeViewModel.addDigit("9")
        }
        binding.button0.setOnClickListener {
            passcodeViewModel.addDigit("0")

        }
        binding.buttonDelete.setOnClickListener {
            passcodeViewModel.removeDigit()
        }
    }

    private fun observePasscodeResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                passcodeViewModel.passcodeResult.collectLatest { result ->
                    result?.let {
                        if (it == "Success") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.success), Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.incorrect_passcode), Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        passcodeViewModel.clearPasscode()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            passcodeViewModel.passcode.collectLatest { passcode ->
                updateDots(passcode.length)
            }
        }
    }

    private fun updateDots(length: Int) {
        val dots = listOf(binding.dot1, binding.dot2, binding.dot3, binding.dot4)

        for (i in 0 until length) {
            dots[i].setImageResource(R.drawable.green)
        }

        for (i in length until dots.size) {
            dots[i].setImageResource(R.drawable.grey)
        }
    }
}
