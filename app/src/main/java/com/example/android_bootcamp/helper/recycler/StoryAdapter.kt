package com.example.android_bootcamp.helper.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_bootcamp.databinding.StoryItemBinding
import com.example.android_bootcamp.helper.data_classes.Story

class StoryAdapter : ListAdapter<Story, StoryAdapter.StoryViewHolder>(StoryDiffCallback()) {

    class StoryViewHolder(private val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {

            binding.titleId.text = story.title
            Glide.with(binding.imageId.context)
                .load(story.imageUrl)
                .into(binding.imageId)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class StoryDiffCallback : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem == newItem
}
