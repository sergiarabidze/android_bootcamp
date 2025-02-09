package com.example.android_bootcamp.common.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.example.android_bootcamp.R
import com.example.android_bootcamp.remote.api.serializable_classes.User
import com.example.android_bootcamp.databinding.UserItemBinding

class UsersAdapter : PagingDataAdapter<User, UsersAdapter.UserViewHolder>(USER_COMPARATOR) {

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(user: User) {
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
        val user = getItem(position)
        user?.let { holder.onBind(it) }
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}

