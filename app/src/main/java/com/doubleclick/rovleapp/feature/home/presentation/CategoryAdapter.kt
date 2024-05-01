package com.doubleclick.rovleapp.feature.home.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemCategoryBinding
import com.doubleclick.rovleapp.feature.home.data.Categories


class CategoryAdapter : ListAdapter<Categories, CategoryAdapter.ViewHolder>(Differ) {

    internal var clickShowCategory: (String) -> Unit = { _ -> }
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
            item: Categories,
            clickShowCategory: (String) -> Unit
        ) {

            binding.name.text = item.name
            if (!item.image.isNullOrEmpty()) {
                binding.image.load(item.image) {
                    crossfade(true)
                    placeholder(R.drawable.image)
                    error(R.drawable.image)
                }
            }



        }

    }

    object Differ : DiffUtil.ItemCallback<Categories>() {
        override fun areItemsTheSame(
            oldItem: Categories,
            newItem: Categories
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Categories,
            newItem: Categories
        ): Boolean {
            return oldItem == newItem
        }
    }
}
