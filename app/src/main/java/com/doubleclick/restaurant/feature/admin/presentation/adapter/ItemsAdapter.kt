package com.doubleclick.restaurant.feature.admin.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemRowDishBinding
import com.doubleclick.restaurant.feature.admin.data.listItems.ItemsData
import com.doubleclick.restaurant.utils.Constant

class ItemsAdapter : ListAdapter<ItemsData, ItemsAdapter.ViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_item_row_dish))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemRowDishBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position))

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemRowDishBinding,
            item: ItemsData
        ) {

            binding.name.text = item.name
            binding.catecory.text = item.category.name
            when (item.vip) {
                "0" -> {
                    binding.type.text = "N"
                    binding.type.setBackgroundResource(R.drawable.bg_gray_circle)
                }

                "1" -> {
                    binding.type.text = "VIP"
                    binding.type.setBackgroundResource(R.drawable.bg_yellow_circle)
                }
            }
            if (!item.image.isNullOrEmpty()) {
                binding.circleImageView.load(Constant.BASE_URL_IMAGE_CATEGORIES + item.image) {
                    crossfade(true)
                    placeholder(R.drawable.image)
                    error(R.drawable.image)
                }
            }

        }

    }

    object Differ : DiffUtil.ItemCallback<ItemsData>() {
        override fun areItemsTheSame(
            oldItem: ItemsData,
            newItem: ItemsData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemsData,
            newItem: ItemsData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
