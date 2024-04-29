package com.doubleclick.restaurant.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.circleimageview.CircleImageView
import com.doubleclick.rovleapp.R

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image : CircleImageView = itemView.findViewById(R.id.image)
    val name :TextView = itemView.findViewById(R.id.name)
}