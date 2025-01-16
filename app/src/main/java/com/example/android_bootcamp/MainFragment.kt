package com.example.android_bootcamp

import android.app.Dialog
import android.os.Bundle
import android.view.View

import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.android_bootcamp.api.Field
import com.example.android_bootcamp.api.FieldList
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.databinding.GenderDialogBinding
import com.example.android_bootcamp.recyclers.InnerRecyclerAdapter
import com.example.android_bootcamp.recyclers.OuterRecyclerAdapter
import kotlinx.serialization.json.Json



class MainFragment :
    FragmentBase<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {
    private val viewModel: VIewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jsonFileString = getJsonDataFromAsset(requireContext(), "field.json")

        val fieldsList: FieldList = Json.decodeFromString(jsonFileString)
        val adapter = OuterRecyclerAdapter(fieldsList, ::addField).apply {
            onChooserClicked = ::getGender
        }
        binding.recyclerId.adapter = adapter
        listener()

    }

    private fun addField(field: Field, input: String) {
        viewModel.addField(field.fieldId, Triple(field.hint, field.required, input))
    }

    private fun listener() {
        binding.registerButtonId.setOnClickListener {
            var isSuccess = true
            viewModel.fields.forEach { (_, value) ->

                val (hint, required, input) = value
                if (required && input.isEmpty()) {
                    Toast.makeText(requireContext(), "$hint is required", Toast.LENGTH_SHORT).show()
                    isSuccess = false
                }

            }
            if (isSuccess) {
                Toast.makeText(requireContext(), "you registered successfully", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun getGender(
        field: Field,
        textView: TextView,
        i: Int,
        innerRecyclerAdapter: InnerRecyclerAdapter
    ) {
        val genderDialogBinding = GenderDialogBinding.inflate(layoutInflater)
        val genderDialog = Dialog(requireContext())
        genderDialog.setContentView(genderDialogBinding.root)
        genderDialog.show()
        var selectedGender = "Other"

        with(genderDialogBinding) {
            genderOptions.setOnCheckedChangeListener { _, checkedId ->
                selectedGender = when (checkedId) {
                    rbMale.id -> "Male"
                    rbFemale.id -> "Female"
                    rbOther.id -> "Other"
                    else -> "Other"
                }

                textView.text = selectedGender
                innerRecyclerAdapter.notifyItemChanged(i)
            }

            tvConfirm.setOnClickListener {
                genderDialog.dismiss()
                innerRecyclerAdapter.notifyItemChanged(i)
                textView.text = selectedGender
                addField(field, selectedGender)
            }
        }
    }
}