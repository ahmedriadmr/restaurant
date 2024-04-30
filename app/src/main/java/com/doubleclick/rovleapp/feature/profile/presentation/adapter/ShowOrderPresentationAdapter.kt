package com.doubleclick.restaurant.feature.profile.presentation.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemPackageInOrderBinding
import com.doubleclick.restaurant.feature.profile.data.orders.showOrder.Item
import com.doubleclick.restaurant.utils.Constant

class ShowOrderPresentationAdapter : ListAdapter<Item, ShowOrderPresentationAdapter.ViewHolder>(Differ) {
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
            val weightText = itemView.context.getString(R.string.weight_placeholder, item.presentation.weight.toString())
            val packageCountText = itemView.context.getString(R.string.package_count_placeholder, item.units)

            binding.weight.text = weightText
            binding.tvPackage.text = packageCountText
            val priceText = if (item.price_after_discount.isNullOrEmpty() || item.price_after_discount == "0") {
                "${item.total} ${Constant.euroSign}"
            } else {
                "${item.price_after_discount} ${Constant.euroSign}"
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