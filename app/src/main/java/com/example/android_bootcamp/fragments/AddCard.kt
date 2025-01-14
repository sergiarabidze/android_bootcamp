package com.example.android_bootcamp.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.helper_clases.FragmentBase
import com.example.android_bootcamp.helper_clases.ItemViewModel
import com.example.android_bootcamp.R
import com.example.android_bootcamp.card.ItemModel
import com.example.android_bootcamp.card.Type
import com.example.android_bootcamp.databinding.FragmentAddCardBinding

class AddCard : FragmentBase<FragmentAddCardBinding>(FragmentAddCardBinding::inflate) {

    private lateinit var itemViewModel: ItemViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemViewModel = ViewModelProvider(requireActivity())[ItemViewModel::class.java]//getting view model instance
        listeners()
    }

    private fun listeners() {
        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                binding.toggleButton.setBackgroundResource(R.drawable.mastercard_custom)
                binding.cardItemId.setImageResource(R.drawable.mastercard)
            } else {
                binding.toggleButton.setBackgroundResource(R.drawable.visa_custom)
                binding.cardItemId.setImageResource(R.drawable.visacard)
            }
        }

        binding.addCardId.setOnClickListener {
            val cardholderName = binding.cardholderNameInput.text.toString().trim()
            val cardNumber = binding.cardNumInput.text.toString().trim()
            val expirationDate = binding.expiresInput.text.toString().trim()
            val cvv = binding.cvvInput.text.toString().trim()

            if (validateInput(cardholderName, cardNumber, expirationDate, cvv)) {
                val newItem = ItemModel(
                    type = if (binding.toggleButton.isChecked) Type.VISA else Type.MASTERCARD,
                    number = cardNumber,
                    holderName = cardholderName,
                    validThru = expirationDate,
                    cvv = cvv
                )
                itemViewModel.addItem(newItem)
                findNavController().popBackStack()
            }
        }
    }

    private fun validateInput(name: String, number: String, expiry: String, cvv: String): Boolean {
        if (name.isEmpty()) {
            showToast("Cardholder name cannot be empty")
            return false
        }
        if (number.length != 16) {
            showToast("Card number must be exactly 16 digits")
            return false
        }
        if (!validateExpiryDate(expiry)) {
            showToast("Expiration date must be in MM/YY format")
            return false
        }
        if (cvv.length != 3) {
            showToast("CVV must be exactly 3 numeric characters")
            return false
        }
        return true
    }

    private fun validateExpiryDate(expiry: String): Boolean {
        if (expiry.length != 5 || expiry[2] != '/') return false
        val month = expiry.substring(0, 2).toIntOrNull()
        val year = expiry.substring(3, 5).toIntOrNull()
        return !(month == null || year == null || month !in 1..12)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
