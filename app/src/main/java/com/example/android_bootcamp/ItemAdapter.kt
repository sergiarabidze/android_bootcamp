package com.example.android_bootcamp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private var items: List<ItemModel>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        //here we are inflating the layout we already defined in the xml layout file it has imageView and two textViews
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        holder.apply {//demonstration of my kotlin skills of using scope functions we could use with also
            image.setImageResource(item.image)
            title.text = item.title
            price.text = item.price
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<ItemModel>) {
        items = newItems
        notifyDataSetChanged()//the function that will update recyclerView when the category is changed
    }


    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.item_image_id)
        val title: TextView = itemView.findViewById(R.id.item_title_id)
        val price: TextView = itemView.findViewById(R.id.item_price_id)
        //assigning UI components to each of the views in the layout in the variables
    }
}
