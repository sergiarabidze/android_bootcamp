package com.example.android_bootcamp

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView

class StatusAdapter(
    private var statusList: List<StatusModel>,
    private val statusChange: (StatusModel) -> Unit//we use this variable to handle status changes in MyOrders fragment
) : RecyclerView.Adapter<StatusAdapter.StatusRecyclerViewHolder>() {

    private var selectedPosition = 0//we keep track of the selected position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.status_recycler_view, parent, false)
        return StatusRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return statusList.size
    }

    override fun onBindViewHolder(
        holder: StatusRecyclerViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {

        val status = statusList[position]

        holder.bind(status, position == selectedPosition)

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = position

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            statusChange(status)
        }


    }

    inner class StatusRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val statusText: TextView = itemView.findViewById(R.id.status_bar_text)

        fun bind(status: StatusModel, isSelected: Boolean) {
            statusText.text = status.status
            //we use isSelected boolean to properly change colo of the selected status
            if (isSelected) {
                itemView.background = AppCompatResources.getDrawable(
                    itemView.context,
                    R.drawable.status_bar_custom // Add this drawable to your resources
                )
                statusText.setTextColor(Color.WHITE)
            } else {
                itemView.setBackgroundColor(Color.WHITE)
                statusText.setTextColor(Color.BLACK)
            }
        }
    }
}