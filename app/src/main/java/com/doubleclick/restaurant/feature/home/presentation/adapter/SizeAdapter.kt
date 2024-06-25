package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.feature.home.data.Categories.Size

class SizeAdapter : ListAdapter<Size, SizeAdapter.ViewHolder>(Differ) {
    internal var clickShowSize: (String , Double) -> Unit = { _,_ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_size_ingredients))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding =
            com.doubleclick.restaurant.databinding.LayoutSizeIngredientsBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickShowSize)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            binding: com.doubleclick.restaurant.databinding.LayoutSizeIngredientsBinding,
            size: Size,
            clickShowSize: (String , Double) -> Unit
        ) {
            binding.sizeIngredients.text = size.name
            itemView.setOnClickListener {
                clickShowSize(size.id , size.price)
            }

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