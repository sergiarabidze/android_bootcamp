package com.example.android_bootcamp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bootcamp.R
import com.example.android_bootcamp.databinding.ItemRecyclerBinding

class ItemAdapter(private val items: List<ItemModel>, private val onItemClick: (ItemModel) -> Unit) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemModel) {
            binding.photoId.setImageResource(item.imageResId)
            binding.titleId.text = item.name
            if (item.color == "red") {
                binding.colorId.setBackgroundResource(R.drawable.red_custom)
            } else if (item.color == "black") {
                binding.colorId.setBackgroundResource(R.drawable.black_custom)
            }
            binding.colorQuantityId.text =
                itemView.context.getString(R.string.qty, item.color, item.quantity.toString())

            binding.priceId.text = itemView.context.getString(R.string.pricee, item.price.toString())
            binding.statusId.text = item.status

            binding.statusId.setOnClickListener {
                if(item.status == "Completed"){
                    onItemClick(item)
                }else{//if the item is active we cant rate it
                    return@setOnClickListener
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
