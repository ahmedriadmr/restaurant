package com.doubleclick.restaurant.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R

class SizesDishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val size: TextView = itemView.findViewById(R.id.size)
    val price: TextView = itemView.findViewById(R.id.price)
}