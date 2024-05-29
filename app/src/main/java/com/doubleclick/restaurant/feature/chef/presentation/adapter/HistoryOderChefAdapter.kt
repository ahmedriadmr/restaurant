package com.doubleclick.restaurant.feature.chef.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.feature.chef.domain.model.Data
import com.doubleclick.restaurant.utils.collapse
import com.doubleclick.restaurant.utils.expand
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryOderChefAdapter(private val data: List<Data>) :
    RecyclerView.Adapter<HistoryOderChefAdapter.HistoryOrderChefViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryOrderChefViewHolder =
        HistoryOrderChefViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_order_chef, parent, false)
        )


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HistoryOrderChefViewHolder, position: Int) {
        holder.table_number.text = buildString {
            append("No.table")
            append(" : ")
            append(data[holder.bindingAdapterPosition].table_number ?: "")
        }
        holder.order_number.text = buildString {
            append("No.Order")
            append(" : ")
            append(data[holder.bindingAdapterPosition].id)
        }
        holder.time.text = buildString {
            append(convertDateFormat(data[holder.bindingAdapterPosition].created_at))
        }
        holder.status.text = buildString {
            append(data[holder.bindingAdapterPosition].status)
        }
        holder.expand.setOnClickListener {
            if (holder.rv_items.isVisible) {
                holder.rv_items.collapse()
            } else {
                holder.rv_items.expand()
            }
        }
        holder.rv_items.adapter = ItemsChefAdapter(data[holder.bindingAdapterPosition].items)

    }
    // Function to convert timestamp to desired format
    private fun convertDateFormat(originalDate: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US)
        val targetFormat = SimpleDateFormat("EEE MMM dd yyyy", Locale.US)
        val date = originalFormat.parse(originalDate)
        return targetFormat.format(date)
    }
    inner class HistoryOrderChefViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val table_number: TextView = itemView.findViewById(R.id.table_number)
        val order_number: TextView = itemView.findViewById(R.id.order_number)
        val time: TextView = itemView.findViewById(R.id.time)
        val status: TextView = itemView.findViewById(R.id.status)
        val expand: ImageView = itemView.findViewById(R.id.expend)
        val rv_items: RecyclerView = itemView.findViewById(R.id.rv_items)
    }

}