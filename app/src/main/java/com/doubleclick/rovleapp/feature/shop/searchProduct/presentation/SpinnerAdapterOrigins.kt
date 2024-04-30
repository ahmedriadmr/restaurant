package com.doubleclick.restaurant.feature.shop.searchProduct.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemCheckBoxBinding
import com.doubleclick.restaurant.feature.shop.searchProduct.data.origins.Origins

class SpinnerAdapterOrigins : ListAdapter<Origins, SpinnerAdapterOrigins.ViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_check_box))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemCheckBoxBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position))
        binding.selected.setOnCheckedChangeListener { _, isChecked ->
            getItem(position).isSelected = isChecked
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemCheckBoxBinding,
            item: Origins
        ) {
            binding.name.text = item.name

        }

    }

    object Differ : DiffUtil.ItemCallback<Origins>() {
        override fun areItemsTheSame(oldItem: Origins, newItem: Origins): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Origins, newItem: Origins): Boolean {
            return oldItem == newItem
        }
    }
}