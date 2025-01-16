package com.example.android_bootcamp.recyclers

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bootcamp.api.Field
import com.example.android_bootcamp.databinding.FieldItemBinding

class InnerRecyclerAdapter(
    private val fields: MutableList<Field>,
    private val onFieldChanged: (Field, String) -> Unit,
) : RecyclerView.Adapter<InnerRecyclerAdapter.InnerViewHolder>() {

    var onInnerChooserClicked: ((Field, TextView, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val binding = FieldItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val field = fields[position]
        holder.bind(field)
    }

    override fun getItemCount(): Int {
        return fields.size
    }

    inner class InnerViewHolder(private val binding: FieldItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(field: Field) {
            binding.itemEditTextId.hint = field.hint

            binding.itemEditTextId.inputType = when (field.fieldType) {
                "input" -> InputType.TYPE_CLASS_TEXT
                "number" -> InputType.TYPE_CLASS_NUMBER
                else -> InputType.TYPE_CLASS_TEXT
            }

                if(fields[adapterPosition].hint == "Gender"){
                    binding.itemEditTextId.visibility = View.GONE
                    binding.text.visibility = View.VISIBLE

                }


                binding.text.setOnClickListener {
                    onInnerChooserClicked?.invoke(field, binding.text, adapterPosition)
                }


            binding.itemEditTextId.doOnTextChanged { input, _, _, _ ->
                onFieldChanged(field, input.toString())
            }

        }
    }
}