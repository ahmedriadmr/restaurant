package com.doubleclick.restaurant.feature.shop.cartDetails.presentation

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemPackageInOrderBinding
import com.doubleclick.restaurant.feature.shop.cartDetails.data.orderDetails.Item
import com.doubleclick.restaurant.utils.Constant.euroSign

class OrderPresentationAdapter : ListAdapter<Item, OrderPresentationAdapter.ViewHolder>(Differ) {
    private lateinit var _binding: LayoutItemPackageInOrderBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.layout_item_package_in_order))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemPackageInOrderBinding.bind(holder.itemView)
        _binding = binding
        holder.bind(getItem(position), binding)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: Item,
            binding: LayoutItemPackageInOrderBinding
        ) {

            binding.weight.text = itemView.context.getString(R.string.weight_placeholder, item.presentation.weight.toString())
            binding.tvPackage.text = itemView.context.getString(R.string.package_text, item.units.toString())
            val priceText = if (item.price_after_discount.isNullOrEmpty() || item.price_after_discount == "0") {
                "${item.total} $euroSign"
            } else {
                "${item.price_after_discount} $euroSign"
            }

            binding.price.text = priceText
        }
    }

    object Differ : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }
    }
}