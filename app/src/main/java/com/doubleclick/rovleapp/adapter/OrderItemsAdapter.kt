package com.doubleclick.restaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.domain.model.carts.get.CartsModel
import com.doubleclick.restaurant.viewHolder.OrderItemsViewHolder
import com.doubleclick.restaurant.R


class OrderItemsAdapter(val carts: List<CartsModel>) :
    RecyclerView.Adapter<OrderItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemsViewHolder {
        return OrderItemsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_order_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrderItemsViewHolder, position: Int) {
        holder.name.text = buildString {
            append(carts[holder.bindingAdapterPosition].size.item.name)
        }
        holder.size.text = buildString {
            append(carts[holder.bindingAdapterPosition].size.name)
        }
        holder.price.text = buildString {
            append(carts[holder.bindingAdapterPosition].size.price * carts[holder.bindingAdapterPosition].number)
        }
        holder.count.text = buildString {
            append("X")
            append(carts[holder.bindingAdapterPosition].number)
        }
    }

    override fun getItemCount(): Int {
        return carts.size
    }

}