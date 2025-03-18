package com.example.android_bootcamp.presentation.features.helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bootcamp.databinding.ExcavatorItemBinding
import com.example.android_bootcamp.presentation.model.ExcavatorPresentation

class ExcavatorAdapter :
    ListAdapter<ExcavatorPresentation, ExcavatorAdapter.ExcavatorViewHolder>(ExcavatorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExcavatorViewHolder {
        val binding = ExcavatorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExcavatorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExcavatorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExcavatorViewHolder(private val binding: ExcavatorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(excavator: ExcavatorPresentation) {
            val balls = "🔸".repeat(excavator.depth)
            binding.titleId.text = if (balls.isNotEmpty()) "$balls ${excavator.name}" else excavator.name
        }
    }
}

class ExcavatorDiffCallback : DiffUtil.ItemCallback<ExcavatorPresentation>() {
    override fun areItemsTheSame(oldItem: ExcavatorPresentation, newItem: ExcavatorPresentation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ExcavatorPresentation, newItem: ExcavatorPresentation): Boolean {
        return oldItem == newItem
    }
}
