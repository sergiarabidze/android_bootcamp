package com.example.android_bootcamp.helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_bootcamp.databinding.StatisticsItemBinding
import com.example.android_bootcamp.remote.Place

class ViewPagerAdapter : ListAdapter<Place, ViewPagerAdapter.ViewHolder>(PlaceDiffCallback()) {

    inner class ViewHolder(private val binding: StatisticsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place) {
            binding.titleId.text = place.title
            binding.cityId.text = place.location
            binding.priceId.text = place.price
            binding.fireNumberId.text = place.reactionCount.toString()
            binding.ratingBar.rating = place.rate?.toFloat() ?: 0.0f

            Glide.with(binding.imageId.context)
                .load(place.cover)
                .into(binding.imageId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StatisticsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }
    }
}

