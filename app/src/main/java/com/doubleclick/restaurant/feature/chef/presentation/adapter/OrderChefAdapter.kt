package com.doubleclick.restaurant.feature.chef.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.feature.chef.domain.model.Data

class OrderChefAdapter(private val data: List<Data>, val onFinished: (order_id: String) -> Unit) :
    RecyclerView.Adapter<OrderChefAdapter.OrderChefViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderChefViewHolder =
        OrderChefViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_chef, parent, false)
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: OrderChefViewHolder, position: Int) {
        holder.finished.setOnClickListener {
            onFinished(data[holder.bindingAdapterPosition].id.toString())
        }
        holder.rv_items.adapter = ItemsChefAdapter(data[holder.bindingAdapterPosition].items)
        holder.table_number.text = buildString {
            append("No.table")
            append(" : ")
            append(data[holder.bindingAdapterPosition].table_number ?: "")
        }
    }

    inner class OrderChefViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val finished: TextView = itemView.findViewById(R.id.finished)
        val table_number: TextView = itemView.findViewById(R.id.table_number)
        val rv_items: RecyclerView = itemView.findViewById(R.id.rv_items)
    }
}