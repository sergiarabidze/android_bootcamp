package com.example.android_bootcamp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.android_bootcamp.databinding.FragmentDeleteConfirmationBottomShetBinding

class DeleteConfirmationBottomSheet(private val onDeleteConfirmed: () -> Unit) : DialogFragment() {

    private lateinit var binding: FragmentDeleteConfirmationBottomShetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeleteConfirmationBottomShetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deleteButton.setOnClickListener {
            //trigger the delete action
            onDeleteConfirmed()//we send back call that will remove item from the pager
            dismiss() //dismiss the BottomSheet
        }

        binding.cancelButton.setOnClickListener {
            dismiss() //dismiss the BottomSheet without doing anything
        }
    }
}
