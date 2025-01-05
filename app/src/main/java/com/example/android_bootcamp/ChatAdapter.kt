package com.example.android_bootcamp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bootcamp.databinding.AdminTextRecyclerBinding
import com.example.android_bootcamp.databinding.NormalUserRecyclerBinding

class ChatterAdapter() : ListAdapter<Chatter,RecyclerView.ViewHolder>(ChatDiffUtil()) {
    companion object {
        private const val NORMAL_USER_VIEW_TYPE = 1
        private const val ADMIN_USER_VIEW_TYPE = 2
    }//constant for types

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {//logic for types
            NORMAL_USER_VIEW_TYPE -> {
                val binding = NormalUserRecyclerBinding.inflate(inflater, parent, false)
                NormalUserViewHolder(binding)
            }
            ADMIN_USER_VIEW_TYPE -> {
                val binding = AdminTextRecyclerBinding.inflate(inflater, parent, false)
                AdminUserViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")//for invalid inputs
    }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            UserType.NORMAL -> NORMAL_USER_VIEW_TYPE
            UserType.ADMIN -> ADMIN_USER_VIEW_TYPE
        }//we need this function to distinguish between types
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {//with smart cast we can use proper onBind method
            is AdminUserViewHolder -> holder.onBind(getItem(position))
            is NormalUserViewHolder -> holder.onBind(getItem(position))
        }
    }

    inner class NormalUserViewHolder(private val binding: NormalUserRecyclerBinding) :RecyclerView.ViewHolder(binding.root){
        fun onBind(chatter: Chatter){
            binding.userText.text = chatter.text
            binding.dateTextUser.text = chatter.time
        }
    }



    inner class AdminUserViewHolder(private val binding: AdminTextRecyclerBinding) :RecyclerView.ViewHolder(binding.root){
        fun onBind(chatter: Chatter){
            binding.adminText.text = chatter.text
            binding.dateText.text = chatter.time
        }
    }
}

class ChatDiffUtil:DiffUtil.ItemCallback<Chatter>(){//diffUtil function for recycler
    override fun areItemsTheSame(oldItem: Chatter, newItem: Chatter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Chatter, newItem: Chatter): Boolean {
        return oldItem == newItem
    }

}