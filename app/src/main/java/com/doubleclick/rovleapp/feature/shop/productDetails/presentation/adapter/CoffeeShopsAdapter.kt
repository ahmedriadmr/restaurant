package com.doubleclick.restaurant.feature.shop.productDetails.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemOurShopsBinding

class CoffeeShopsAdapter : ListAdapter<com.doubleclick.restaurant.feature.shop.response.CoffeeShop, CoffeeShopsAdapter.ViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_our_shops))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemOurShopsBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemOurShopsBinding,
            item: com.doubleclick.restaurant.feature.shop.response.CoffeeShop
        ) {
            binding.coffeeShopName.text = item.name
            binding.coffeeShopAddress.text = item.address

        }

    }

    object Differ : DiffUtil.ItemCallback<com.doubleclick.restaurant.feature.shop.response.CoffeeShop>() {
        override fun areItemsTheSame(oldItem: com.doubleclick.restaurant.feature.shop.response.CoffeeShop, newItem: com.doubleclick.restaurant.feature.shop.response.CoffeeShop): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: com.doubleclick.restaurant.feature.shop.response.CoffeeShop, newItem: com.doubleclick.restaurant.feature.shop.response.CoffeeShop): Boolean {
            return oldItem == newItem
        }
    }
}