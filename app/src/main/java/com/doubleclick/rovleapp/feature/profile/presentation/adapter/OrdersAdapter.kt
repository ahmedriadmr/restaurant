package com.doubleclick.restaurant.feature.profile.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemYourOrdersBinding
import com.doubleclick.restaurant.feature.profile.data.orders.listOrders.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrdersAdapter(private val isAll: Boolean) : ListAdapter<Order, OrdersAdapter.ViewHolder>(Differ) {

    internal var clickDetails: (String) -> Unit = { _ -> }
    internal var clickRateOrder: (String) -> Unit = {  _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_your_orders))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemYourOrdersBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickDetails,  clickRateOrder)
    }


    override fun getItemCount() = when {
        currentList.isNotEmpty() -> if (isAll) currentList.size else currentList.size.coerceAtMost(3)
        else -> currentList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemYourOrdersBinding,
            order: Order,
            clickDetails: (String) -> Unit,
            clickRateOrder: (String) -> Unit
        ) {


            binding.product.text = order.products.joinToString(", ") { it.product_name }



            fun convertDateFormat(inputDate: String): String {
                val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val outputFormat = SimpleDateFormat("EEE MMM dd yyyy", Locale.getDefault())

                val date: Date = inputFormat.parse(inputDate) ?: Date()
                return outputFormat.format(date)
            }

            val inputDate = order.created_at
            val outputDate = convertDateFormat(inputDate)
            binding.orderDate.text = outputDate

            binding.details.setOnClickListener {
                clickDetails(order.id)
            }

            binding.rateOrder.setOnClickListener {
                clickRateOrder(order.id)
            }
        }

    }

    object Differ : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
}