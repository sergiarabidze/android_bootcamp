package com.example.android_bootcamp.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bootcamp.api.Field
import com.example.android_bootcamp.api.FieldList
import com.example.android_bootcamp.databinding.RecyclerItemBinding

class OuterRecyclerAdapter(
    private val fieldsList: FieldList,
    private val onFieldListChanged: (Field, String) -> Unit,
) : RecyclerView.Adapter<OuterRecyclerAdapter.OuterViewHolder>() {

    var onChooserClicked: ((Field, TextView, Int, InnerRecyclerAdapter) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterViewHolder {
        val binding =
            RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OuterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OuterViewHolder, position: Int) {
        val fieldList = fieldsList[position]
        holder.bind(fieldList)
    }

    override fun getItemCount(): Int {
        return fieldsList.size
    }

    inner class OuterViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fieldList: List<Field>) {
            val innerRecyclerAdapter =
                InnerRecyclerAdapter(
                    fieldList.toMutableList()
                ) { field, value ->
                    onFieldListChanged(field, value)
                }

            innerRecyclerAdapter.onInnerChooserClicked = { field, text, position ->
                onChooserClicked?.invoke(field, text, position, innerRecyclerAdapter)
            }

            binding.recyclerOfRecyclerId.adapter = innerRecyclerAdapter
        }
    }
}
