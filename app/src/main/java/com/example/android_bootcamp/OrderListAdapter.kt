package com.example.android_bootcamp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class OrderListAdapter(var ondetails: (OrderModel) -> Unit) :
    ListAdapter<OrderModel, OrderListAdapter.OrderViewHolder>(LocationDiffCallback()) {
//we need additional variable to handle details button in main fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_recycler_view, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderIdTextView: TextView = itemView.findViewById(R.id.order_num_id)
        private val trackingNumberTextView: TextView = itemView.findViewById(R.id.tracking_num_id)
        private val quantityTextView: TextView = itemView.findViewById(R.id.quantity_num_id)
        private val priceTextView: TextView = itemView.findViewById(R.id.subtotal_price_id)
        private val statusTextView: TextView = itemView.findViewById(R.id.status_id)
        private val dateTextView: TextView = itemView.findViewById(R.id.date_id)
        private val detailsButton: TextView = itemView.findViewById(R.id.details_id)

        //we assign all values to the recycler
        @SuppressLint("SetTextI18n")
        fun bind(order: OrderModel) {
            orderIdTextView.text = order.orderId.toString()
            trackingNumberTextView.text = order.trackingNumber
            quantityTextView.text = order.qunatity.toString()
            priceTextView.text = "$${order.price}"
            statusTextView.text = order.status.status
            dateTextView.text =
                order.date.formatTimestamp()//order is Long in the data class but we transform it to timestamp

            when (order.status.status) {
                "Pending" -> statusTextView.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.pending)
                )

                "Cancelled" -> statusTextView.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.canceled)
                )

                "Delivered" -> statusTextView.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.delivered)
                )
            }//simple switch statement for handling color of status
            detailsButton.setOnClickListener {
                ondetails(order)
            }//and when details is clicked we should handle it in main fragment(myOrdersFragment)
        }

    }

}


class LocationDiffCallback : DiffUtil.ItemCallback<OrderModel>() {
    override fun areItemsTheSame(oldItem: OrderModel, newItem: OrderModel): Boolean {
        return newItem.orderId == oldItem.orderId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: OrderModel, newItem: OrderModel): Boolean {
        return oldItem == newItem
    }

}

