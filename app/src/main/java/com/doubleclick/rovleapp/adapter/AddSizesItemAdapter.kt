package com.doubleclick.restaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.domain.model.sizes.Sizes
import com.doubleclick.restaurant.viewHolder.AddSizesItemViewHolder
import com.doubleclick.rovleapp.R


class AddSizesItemAdapter(val sizes: List<Sizes>) : RecyclerView.Adapter<AddSizesItemViewHolder>() {

    private var onClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSizesItemViewHolder {
        return AddSizesItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_size, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddSizesItemViewHolder, position: Int) {
        holder.size_name.text = sizes[holder.bindingAdapterPosition].name
        holder.size_price.text = sizes[holder.bindingAdapterPosition].price.toString()
        holder.delete.setOnClickListener {
            onClick?.let {
                it(holder.absoluteAdapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return sizes.size
    }

    fun onItemClick(listener: (Int) -> Unit) {
        onClick = listener
    }

}