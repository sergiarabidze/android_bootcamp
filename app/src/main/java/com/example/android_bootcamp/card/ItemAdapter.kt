package com.example.android_bootcamp.card

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bootcamp.R
import com.example.android_bootcamp.databinding.CardRecyclerBinding

class ItemAdapter(private val onDeleteClick: (ItemModel) -> Unit) : ListAdapter<ItemModel, ItemAdapter.ItemViewHolder>(
    ItemDiffCallback()
) {

    class ItemViewHolder(val binding: CardRecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CardRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            cardNum.text = formatCardNumber(item.number)
            cardHolderName.text = item.holderName
            validThruId.text = item.validThru
            if (item.type == Type.VISA) {//if we have visa type we change background
                backgroundId.setImageResource(R.drawable.visacard)
            } else {
                backgroundId.setImageResource(R.drawable.mastercard)
            }

            holder.itemView.setOnLongClickListener{
                onDeleteClick(item)
                true
            }//on long click we delete card
        }
    }

    private fun formatCardNumber(cardNumber: String): String {
        return cardNumber.chunked(4).joinToString("  ")
    }//there are spaces after every 4 digit

    class ItemDiffCallback : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }
    }
}
