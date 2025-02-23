package com.example.android_bootcamp.helper.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_bootcamp.R
import com.example.android_bootcamp.databinding.PostItemBinding
import com.example.android_bootcamp.helper.data_classes.Post

class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PostViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.description.text = post.title
            binding.usernameId.text =
                binding.root.context.getString(
                    R.string.full_name,
                    post.owner.firstName,
                    post.owner.lastName
                )
            binding.dateId.text = post.owner.postDate
            Glide.with(binding.root.context)
                .load(post.owner.profile)
                .placeholder(R.drawable.userr)
                .error(R.drawable.userr)
                .into(binding.profilePic)
            binding.commentsAmount.text =
                binding.root.context.getString(R.string.comments, post.comments.toString())
            binding.likesAmount.text =
                binding.root.context.getString(R.string.likes, post.likes.toString())

            val imageViews = listOf(binding.image1, binding.image2, binding.image3)
            val images = post.images
            for (i in images.indices) {
                Glide.with(binding.root.context).load(images[i]).into(imageViews[i])
            }

            when (images.size) {
                1 -> {
                    imageViews[0].visibility = View.VISIBLE
                }
                2 -> {

                    imageViews[0].visibility = View.VISIBLE
                    imageViews[1].visibility = View.VISIBLE

                    imageViews[0].layoutParams = ConstraintLayout.LayoutParams(0, 0).apply {
                        matchConstraintPercentWidth = 0.5f
                        matchConstraintPercentHeight = 1f
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        endToStart = imageViews[1].id
                        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                        marginEnd = 3

                    }

                    imageViews[1].layoutParams = ConstraintLayout.LayoutParams(0, 0).apply {
                        matchConstraintPercentWidth = 0.5f
                        matchConstraintPercentHeight = 1f
                        startToEnd = imageViews[0].id
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                        marginStart = 3
                    }
                }

                3 -> {
                    imageViews.forEach { it.visibility = View.VISIBLE }

                    imageViews[0].layoutParams = ConstraintLayout.LayoutParams(0, 0).apply {
                        matchConstraintPercentWidth = 0.5f
                        matchConstraintPercentHeight = 1f
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        endToStart = imageViews[1].id
                        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    }

                    imageViews[1].layoutParams = ConstraintLayout.LayoutParams(0, 0).apply {
                        matchConstraintPercentWidth = 0.5f
                        matchConstraintPercentHeight = 0.5f
                        startToEnd = imageViews[0].id
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        bottomToTop = imageViews[2].id
                    }

                    imageViews[2].layoutParams = ConstraintLayout.LayoutParams(0, 0).apply {
                        matchConstraintPercentWidth = 0.5f
                        matchConstraintPercentHeight = 0.5f
                        startToEnd = imageViews[0].id
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        topToBottom = imageViews[1].id
                        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    }
                }

                else -> {
                    binding.postImages.visibility = View.GONE
                }
            }

        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
    }
}
