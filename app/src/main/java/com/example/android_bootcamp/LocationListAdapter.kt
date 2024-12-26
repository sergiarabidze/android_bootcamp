package com.example.android_bootcamp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class LocationListAdapter(
    private val onEditClick: (ItemModel) -> Unit,//this variable for storing lambda function for edit button
    private val onDeleteClick: (ItemModel) -> Unit//for delete button
) : ListAdapter<ItemModel, LocationListAdapter.ItemViewHolder>(LocationDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_item_recycler, parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.bind(getItem(position))

    }


    inner class ItemViewHolder(locationView: View) : RecyclerView.ViewHolder(locationView) {
        val edit: TextView = itemView.findViewById(R.id.edit_location_id)
        val checkBox: CheckBox = itemView.findViewById(R.id.check_box_id)
        private val infoText: TextView = itemView.findViewById(R.id.location_info_id)
        private val addressText: TextView = itemView.findViewById(R.id.address_id)
        private val icon: ImageView = itemView.findViewById(R.id.icon_id)

        fun bind(location: ItemModel) {
            infoText.text = location.info //Set text for location info
            addressText.text = location.address //Set text for address info
            icon.setImageResource(location.icon)

            itemView.setOnLongClickListener {
                onDeleteClick(location)
                true
            }//if user clicks for a while we delete the item

            edit.setOnClickListener {
                if (!checkBox.isChecked) {
                    return@setOnClickListener
                }//if the check is not checked we return
                onEditClick(location)
            }

        }
    }
}

class LocationDiffCallback : DiffUtil.ItemCallback<ItemModel>() {
    override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return newItem.id == oldItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return oldItem == newItem
    }
}