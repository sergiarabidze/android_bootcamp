package com.example.android_bootcamp

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    private val categories: List<CategoryModel>,
    private val onCategorySelected: (CategoryModel) -> Unit
//here we add this input because we need to execute the function for the category that is clicked in ths recyclerView
) : RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>() {

    private var selectedPosition = 0// we keep track of the position which is selected

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_recycler_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val category = categories[position]
        holder.title.text = category.name
        if (position == selectedPosition) {
            holder.itemView.background = AppCompatResources.getDrawable(
                holder.itemView.context,
                R.drawable.category_shape_clicked
            )
            holder.title.setTextColor(Color.WHITE)
            //if the category is clicked we should change UI of it
        } else {
            holder.itemView.background = AppCompatResources.getDrawable(
                holder.itemView.context,
                R.drawable.category_shape_not_clicked
            )
            holder.title.setTextColor(holder.itemView.context.getColor(R.color.text_color))
        }

        holder.itemView.setOnClickListener {

            val previousPosition =
                selectedPosition//here when the category is clicked position is updated
            selectedPosition = position//and the selected position is updated also

            notifyItemChanged(previousPosition)
            //we notify the adapter that the item has changed so previous becomes default color and the new one is clicked color
            notifyItemChanged(selectedPosition)

            onCategorySelected(category)//here we call the function in the fragment which will take the category as an input and then do the filtering
        }
    }

    override fun getItemCount(): Int = categories.size


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.category_text_id)
    }
}
