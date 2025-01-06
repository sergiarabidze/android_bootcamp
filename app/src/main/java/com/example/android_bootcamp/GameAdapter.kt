package com.example.android_bootcamp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bootcamp.databinding.ButtonItemBinding

class GameAdapter( val onItemClick:  (ButtonModel) -> Unit ) : ListAdapter<ButtonModel, GameAdapter.GameViewHolder>(GameDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ButtonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val buttonModel = getItem(position)
        holder.onBind(buttonModel)
    }

    inner class GameViewHolder(private val binding: ButtonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val button = binding.buttonId

        fun onBind(buttonModel: ButtonModel) {
            when (buttonModel.type) {
                Type.UNCHECKED -> {
                    button.setImageResource(R.color.white)
                    button.isEnabled = true
                }
                Type.FIRST -> {
                    button.setImageResource(R.drawable.round)
                    button.isEnabled = false
                }
                Type.SECOND -> {
                    button.setImageResource(R.drawable.iqsiki)
                    button.isEnabled = false
                }
            }

            binding.root.setOnClickListener {
                if (buttonModel.type == Type.UNCHECKED) {
                    onItemClick(buttonModel)
                }
            }
        }
    }
}

class GameDiffUtil : DiffUtil.ItemCallback<ButtonModel>() {

    override fun areItemsTheSame(oldItem: ButtonModel, newItem: ButtonModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ButtonModel, newItem: ButtonModel): Boolean {
        return oldItem == newItem
    }
}
