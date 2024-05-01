package com.doubleclick.restaurant.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.circleimageview.CircleImageView
import com.doubleclick.restaurant.R

class DishRowItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val circleImageView: CircleImageView = itemView.findViewById(R.id.circleImageView)
    val name: TextView = itemView.findViewById(R.id.name)
    val type: TextView = itemView.findViewById(R.id.type)
    val category: TextView = itemView.findViewById(R.id.catecory)
    val option: ImageView = itemView.findViewById(R.id.option)

}