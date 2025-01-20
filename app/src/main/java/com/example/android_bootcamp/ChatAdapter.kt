package com.example.android_bootcamp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_bootcamp.databinding.ChatItemBinding

class ChatAdapter : ListAdapter<Chat, ChatAdapter.ChatViewHolder>(ChatDiffCallback()) {

    inner class ChatViewHolder(private val binding: ChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chat: Chat) {
            with(binding) {
                if (!chat.image.isNullOrEmpty()) {
                    Glide.with(imageId.context)
                        .load(chat.image)
                        .placeholder(R.drawable.user)
                        .into(imageId)
                } else {
                    imageId.setImageDrawable(
                        AppCompatResources.getDrawable(
                            binding.root.context,
                            R.drawable.user
                        )
                    )
                }

                ownerId.text = chat.owner

                if (chat.lastMessageType == Type.TEXT) {
                    lastText.text = chat.last_message
                }

                timeId.text = chat.last_active
                if (chat.unread_messages.toString() == "0") {
                    lastReadId.visibility = View.GONE
                } else {
                    lastReadId.text = chat.unread_messages.toString()
                }

                if (chat.is_typing) {
                    lastReadId.visibility = View.GONE
                    dotsId.visibility = View.VISIBLE
                }
                if (chat.lastMessageType == Type.VOICE) {
                    lastText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.recorder, 0, 0, 0)
                    lastText.text = binding.root.context.getString(R.string.sent_a_voice_message)
                } else if (chat.lastMessageType == Type.FILE) {
                    lastText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.attachment, 0, 0, 0)
                    lastText.text = binding.root.context.getString(R.string.sent_an_attachment)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = getItem(position)
        holder.bind(chat)
    }
}

class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }
}
