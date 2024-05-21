package com.doubleclick.restaurant.feature.admin.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemSelectBoxBinding
import com.doubleclick.restaurant.feature.home.data.Categories.Categories


class SelectCategoryItemAdapter: ListAdapter<Categories, SelectCategoryItemAdapter.ViewHolder>(Differ) {
    internal var clickListenerChooseCategory: (String) -> Unit = {_ ->}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_item_select_box))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemSelectBoxBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position),clickListenerChooseCategory)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemSelectBoxBinding,
            item: com.doubleclick.restaurant.feature.home.data.Categories.Categories,
            clickListenerChooseCategory: (String) -> Unit = {_ ->}
        ) {

            binding.selected.setOnClickListener {
                currentList.forEach { it.isSelected = false }
                item.isSelected = true
                notifyDataSetChanged()
                clickListenerChooseCategory(item.id.toString())
            }

            binding.name.text = item.name

            binding.selected.isChecked = item.isSelected // Set the initial checked state



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
