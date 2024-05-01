package com.doubleclick.restaurant.viewHolder

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R

class SelectCategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name)
    val select_box: CheckBox = itemView.findViewById(R.id.select_box)
}