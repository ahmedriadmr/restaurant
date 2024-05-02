package com.doubleclick.restaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.domain.model.items.get.Item
import com.doubleclick.domain.model.items.get.Size
import com.doubleclick.domain.ts.OnAddToCart
import com.doubleclick.domain.ts.OnSizeSelected
import com.doubleclick.restaurant.viewHolder.RestaurantDishViewHolder
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.utils.Constant.BASE_URL_IMAGE_ITEMS

class RestaurantDishAdapter(val items: List<Item>, val onAddToCart: OnAddToCart) :
    RecyclerView.Adapter<RestaurantDishViewHolder>() , OnSizeSelected{

    lateinit var sizeSelected: Size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantDishViewHolder {
        return RestaurantDishViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_dish_in_menu, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RestaurantDishViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(BASE_URL_IMAGE_ITEMS + items[holder.absoluteAdapterPosition].image)
            .into(holder.image_food)
        holder.name_food.text = buildString {
            append(items[holder.absoluteAdapterPosition].name)
        }
        holder.name_des.text = buildString {
            append(items[holder.absoluteAdapterPosition].description)
        }
        if (items[holder.absoluteAdapterPosition].sizes.isNotEmpty()) {
            holder.rv_size.adapter = SizesDishAdapter(items[holder.absoluteAdapterPosition].sizes,this@RestaurantDishAdapter)
        }
        holder.add_to_cart.setOnClickListener {
            onAddToCart.addToCart(items[holder.absoluteAdapterPosition],sizeSelected)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun sizeSelected(size: Size) {
        sizeSelected = size
    }

}