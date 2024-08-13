package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.LayoutItemCategoryBinding
import com.doubleclick.restaurant.feature.home.data.Categories.Item
import com.doubleclick.restaurant.utils.Constant


class CategoryAdapter : ListAdapter<com.doubleclick.restaurant.feature.home.data.Categories.Categories, CategoryAdapter.ViewHolder>(Differ) {

    var onCategoryClick: (List<Item>) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onCategoryClick)
    }

    override fun submitList(list: List<com.doubleclick.restaurant.feature.home.data.Categories.Categories>?) {
        list?.let { categoryList ->
            if (categoryList.none { it.isClicked }) {
                categoryList.firstOrNull()?.let { firstItem ->
                    firstItem.isClicked = true
                    onCategoryClick(firstItem.items)
                }
            }
        }
        super.submitList(list)
    }

    inner class ViewHolder(private val binding: LayoutItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: com.doubleclick.restaurant.feature.home.data.Categories.Categories, clickShowCategory: (List<Item>) -> Unit) {
            binding.name.text = item.name
            binding.name.setTextColor(if (item.isClicked) ContextCompat.getColor(itemView.context, R.color.black) else ContextCompat.getColor(itemView.context, R.color.gray_dark))

            // Load image with fallback for null or empty URL
            binding.image.load(Constant.BASE_URL_IMAGE_CATEGORIES + item.image) {
                crossfade(true)
                placeholder(R.drawable.group)
                error(R.drawable.group)
                fallback(R.drawable.group)
            }

            itemView.setOnClickListener {
                clickShowCategory(item.items)
                currentList.forEach { it.isClicked = false }
                item.isClicked = true
                binding.name.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                notifyDataSetChanged()
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
