package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.feature.home.data.searchOrders.response.Item
import com.doubleclick.restaurant.utils.Constant

class ItemsInOrderAdapter : ListAdapter<Item, ItemsInOrderAdapter.ViewHolder>(Differ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_item_my_old_orders))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = com.doubleclick.restaurant.databinding.LayoutItemMyOldOrdersBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position))

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            binding: com.doubleclick.restaurant.databinding.LayoutItemMyOldOrdersBinding,
            item: Item,
        ) {

//            binding.name.text = item.
            binding.sizeName.text = item.size_name
            binding.quantity.text = "X${item.number}"
            binding.price.text = "${Constant.dollarSign}${item.total}"



        }

    }


    object Differ : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}