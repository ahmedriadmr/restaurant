package com.doubleclick.restaurant.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R

class OrderItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name)
    val size: TextView = itemView.findViewById(R.id.size)
    val count: TextView = itemView.findViewById(R.id.count)
    val price: TextView = itemView.findViewById(R.id.price)
}