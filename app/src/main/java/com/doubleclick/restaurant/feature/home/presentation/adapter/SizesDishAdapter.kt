package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemSizeDishBinding
import com.doubleclick.restaurant.feature.home.data.Size


class SizesDishAdapter: ListAdapter<Size, SizesDishAdapter.ViewHolder>(Differ) {

    internal var clickShowSize: (String , Double) -> Unit = { _,_ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_item_size_dish))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemSizeDishBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickShowSize)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemSizeDishBinding,
            item: Size,
            clickShowSize: (String , Double) -> Unit
        ) {

            binding.size.text = item.name
            itemView.setOnClickListener {
                clickShowSize(item.id , item.price)
            }

        }

    }

    object Differ : DiffUtil.ItemCallback<Size>() {
        override fun areItemsTheSame(oldItem: Size, newItem: Size): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Size, newItem: Size): Boolean {
            return oldItem == newItem
        }
    }
}