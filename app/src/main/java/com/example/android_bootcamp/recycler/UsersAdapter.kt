package com.example.android_bootcamp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_bootcamp.R
import com.example.android_bootcamp.api.User
import com.example.android_bootcamp.databinding.UserItemBinding

class UsersAdapter(private var items: List<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = items[position]
        holder.onBind(user)
    }
}
