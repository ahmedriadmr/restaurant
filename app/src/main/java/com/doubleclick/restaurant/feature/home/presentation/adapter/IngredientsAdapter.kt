package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.feature.admin.data.addProduct.request.Ingredient

class IngredientsAdapter : ListAdapter<Ingredient, IngredientsAdapter.ViewHolder>(Differ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_size_ingredients))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding =
            com.doubleclick.restaurant.databinding.LayoutSizeIngredientsBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position))

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            binding: com.doubleclick.restaurant.databinding.LayoutSizeIngredientsBinding,
            ingredients: Ingredient
        ) {
            binding.sizeIngredients.text = ingredients.name

        }

    }


    object Differ : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(
            oldItem: Ingredient,
            newItem: Ingredient
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Ingredient,
            newItem: Ingredient
        ): Boolean {
            return oldItem == newItem
        }
    }
}