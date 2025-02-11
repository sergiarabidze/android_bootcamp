package com.example.android_bootcamp.common.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_bootcamp.R
import com.example.android_bootcamp.databinding.UserItemBinding
import com.example.android_bootcamp.local.room.UserEntity

class UsersAdapter : PagingDataAdapter<UserEntity, UsersAdapter.UserViewHolder>(USER_COMPARATOR) {

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(userEntity: UserEntity) {
            binding.emailId.text = userEntity.email
            binding.fullNameId.text =
                binding.root.context.getString(R.string.fullname, userEntity.firstName, userEntity.lastName)

            with(binding.root.context) {
                Glide.with(this)
                    .load(userEntity.avatar)
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
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
