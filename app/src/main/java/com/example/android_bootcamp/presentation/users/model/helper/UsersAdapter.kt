package com.example.android_bootcamp.presentation.users.model.helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_bootcamp.R
import com.example.android_bootcamp.databinding.UserItemBinding
import com.example.android_bootcamp.domain.model.UserDomain
import com.example.android_bootcamp.presentation.users.model.user.UserPresentation

class UsersAdapter : PagingDataAdapter<UserPresentation, UsersAdapter.UserViewHolder>(USER_COMPARATOR) {

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(user: UserPresentation) {
            binding.emailId.text = user.email
            binding.fullNameId.text =
                binding.root.context.getString(R.string.fullname, user.firstName, user.lastName)

            with(binding.root.context) {
                Glide.with(this)
                    .load(user.avatar)
                    .into(binding.imageId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userEntity = getItem(position)
        userEntity?.let { holder.onBind(it) }
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<UserPresentation>() {
            override fun areItemsTheSame(oldItem: UserPresentation, newItem: UserPresentation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserPresentation, newItem: UserPresentation): Boolean {
                return oldItem == newItem
            }
        }
    }
}
