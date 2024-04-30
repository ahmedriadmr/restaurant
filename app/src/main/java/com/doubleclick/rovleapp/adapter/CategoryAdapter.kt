package com.doubleclick.restaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.domain.model.category.get.Category
import com.doubleclick.restaurant.viewHolder.CategoryViewHolder
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.utils.Constant.BASE_URL_IMAGE


class CategoryAdapter(val categories: List<Category>) : RecyclerView.Adapter<CategoryViewHolder>() {

    private var onClick: ((Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.name.text = buildString {
            append(categories[holder.absoluteAdapterPosition].name)
        }
        Glide.with(holder.itemView).load(BASE_URL_IMAGE + categories[holder.absoluteAdapterPosition].image)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onClick?.let {
                it(categories[holder.absoluteAdapterPosition].id)
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun onCategoryClick(listener: (Int) -> Unit) {
        onClick = listener
    }

}