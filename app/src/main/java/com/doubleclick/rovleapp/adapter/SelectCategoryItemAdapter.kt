package com.doubleclick.restaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.domain.model.category.get.Category
import com.doubleclick.domain.ts.SelectedCategoryInterface
import com.doubleclick.restaurant.viewHolder.SelectCategoryItemViewHolder
import com.doubleclick.restaurant.R


class SelectCategoryItemAdapter(
    val categories: List<Category>,
    val selectedCategoryInterface: SelectedCategoryInterface
) : RecyclerView.Adapter<SelectCategoryItemViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectCategoryItemViewHolder {
        return SelectCategoryItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_select_box, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SelectCategoryItemViewHolder, position: Int) {

        holder.select_box.setOnClickListener {
            if (holder.select_box.isChecked) {
                selectedCategoryInterface.selceted(categories[holder.bindingAdapterPosition])
            } else {
                selectedCategoryInterface.unselceted(categories[holder.bindingAdapterPosition])
            }
        }

        holder.name.text = categories[holder.bindingAdapterPosition].name

    }

    override fun getItemCount(): Int {
        return categories.size
    }

}