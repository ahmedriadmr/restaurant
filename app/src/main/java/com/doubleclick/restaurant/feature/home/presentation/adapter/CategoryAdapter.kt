package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemCategoryBinding
import com.doubleclick.restaurant.feature.home.data.Categories.Item
import com.doubleclick.restaurant.utils.Constant


class CategoryAdapter : ListAdapter<com.doubleclick.restaurant.feature.home.data.Categories.Categories, CategoryAdapter.ViewHolder>(Differ) {

    internal var clickShowCategory: (List<Item>) -> Unit = { _ -> }
    private var selectedPosition: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_item_category))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemCategoryBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickShowCategory)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemCategoryBinding,
            item: com.doubleclick.restaurant.feature.home.data.Categories.Categories,
            clickShowCategory: (List<Item>) -> Unit
        ) {

            binding.name.text = item.name
            if (!item.image.isNullOrEmpty()) {
                binding.image.load(Constant.BASE_URL_IMAGE_CATEGORIES + item.image) {
                    crossfade(true)
                    placeholder(R.drawable.image)
                    error(R.drawable.image)
                }
            }

            // Set text color based on selection
            if (bindingAdapterPosition == selectedPosition) {
                binding.name.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                // Trigger clickShowCategory for the first item
                if (bindingAdapterPosition == 0) {
                    itemView.post {
                        clickShowCategory.invoke(item.items)
                    }
                }
            } else {
                binding.name.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray_dark))
            }

            // Set click listener for category item
            itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = bindingAdapterPosition

                clickShowCategory.invoke(item.items)

                notifyItemChanged(previousPosition)
                notifyItemChanged(bindingAdapterPosition)
            }
        }
    }

    object Differ : DiffUtil.ItemCallback<com.doubleclick.restaurant.feature.home.data.Categories.Categories>() {
        override fun areItemsTheSame(
            oldItem: com.doubleclick.restaurant.feature.home.data.Categories.Categories,
            newItem: com.doubleclick.restaurant.feature.home.data.Categories.Categories
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: com.doubleclick.restaurant.feature.home.data.Categories.Categories,
            newItem: com.doubleclick.restaurant.feature.home.data.Categories.Categories
        ): Boolean {
            return oldItem == newItem
        }
    }
}
