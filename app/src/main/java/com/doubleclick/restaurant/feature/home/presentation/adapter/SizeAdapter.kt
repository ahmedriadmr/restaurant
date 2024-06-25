package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.feature.home.data.Categories.Size

class SizeAdapter : ListAdapter<Size, SizeAdapter.ViewHolder>(Differ) {
    internal var clickShowSize: (String, Double) -> Unit = { _, _ -> }

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_size_ingredients))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding =
            com.doubleclick.restaurant.databinding.LayoutSizeIngredientsBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position))

        // Apply background and text color based on selection
        if (position == selectedPosition) {
            binding.sizeIngredients.setBackgroundResource(R.drawable.bg_black_r20)
            binding.sizeIngredients.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
        } else {
            binding.sizeIngredients.setBackgroundResource(R.drawable.bg_white_r20)
            binding.sizeIngredients.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.black))
        }

        // Handle item click
        holder.itemView.setOnClickListener {
            clickShowSize(getItem(position).id, getItem(position).price)
            val previousPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            binding: com.doubleclick.restaurant.databinding.LayoutSizeIngredientsBinding,
            size: Size
        ) {
            binding.sizeIngredients.text = size.name
        }
    }

    object Differ : DiffUtil.ItemCallback<Size>() {
        override fun areItemsTheSame(
            oldItem: Size,
            newItem: Size
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Size,
            newItem: Size
        ): Boolean {
            return oldItem == newItem
        }
    }
}
