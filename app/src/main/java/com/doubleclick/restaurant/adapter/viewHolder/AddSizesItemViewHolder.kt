package com.doubleclick.restaurant.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R

class AddSizesItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val size_name: TextView = itemView.findViewById(R.id.size_name)
    val size_price: TextView = itemView.findViewById(R.id.size_price)
    val delete: ImageView = itemView.findViewById(R.id.delete)
}