package com.doubleclick.restaurant.feature.chef.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.feature.chef.domain.model.Item

class ItemsChefAdapter(private val items: List<Item>) :
    RecyclerView.Adapter<ItemsChefAdapter.ItemsChefViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsChefViewHolder =
        ItemsChefViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_order_chef, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemsChefViewHolder, position: Int) {
        holder.name.text = buildString {
            append(items[holder.bindingAdapterPosition].item.name)
        }
        holder.count.text = buildString {
            append("X")
            append(items[holder.bindingAdapterPosition].number)
        }
        holder.size.text = buildString {
            append(items[holder.bindingAdapterPosition].size_name)
        }
    }

    inner class ItemsChefViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val size: TextView = itemView.findViewById(R.id.size)
        val count: TextView = itemView.findViewById(R.id.count)
    }
}