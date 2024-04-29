package com.doubleclick.restaurant.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R

class RestaurantDishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image_food: ImageView = itemView.findViewById(R.id.image_food)
    val name_food: TextView = itemView.findViewById(R.id.name_food)
    val name_des: TextView = itemView.findViewById(R.id.name_des)
    val add_to_cart: ImageView = itemView.findViewById(R.id.add_to_cart)
    val rv_size: RecyclerView = itemView.findViewById(R.id.rv_size)

}