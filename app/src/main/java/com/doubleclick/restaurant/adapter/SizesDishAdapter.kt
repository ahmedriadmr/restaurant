package com.doubleclick.restaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.domain.model.items.get.Size
import com.doubleclick.domain.ts.OnSizeSelected
import com.doubleclick.restaurant.viewHolder.SizesDishViewHolder
import com.doubleclick.restaurant.R


class SizesDishAdapter(val sizes: List<Size>, val onSizeSelected: OnSizeSelected) :
    RecyclerView.Adapter<SizesDishViewHolder>() {

    private var lastCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizesDishViewHolder {
        return SizesDishViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_size_dish, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SizesDishViewHolder, position: Int) {
        holder.size.isSelected = true
        holder.size.text = buildString {
            append(sizes[holder.bindingAdapterPosition].name)
        }
        holder.price.text = buildString {
            append(sizes[holder.bindingAdapterPosition].price)
        }
        if (sizes[holder.bindingAdapterPosition].id == lastCheckedPosition) {
            holder.size.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.size.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_circle_black)
        } else {
            holder.size.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.bg_color_black
                )
            )
            holder.size.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_et_black)
        }
        holder.size.setOnClickListener {
            lastCheckedPosition = sizes[holder.bindingAdapterPosition].id
            onSizeSelected.sizeSelected(sizes[holder.bindingAdapterPosition])
            notifyItemRangeChanged(0,sizes.size)
        }
    }

    override fun getItemCount(): Int {
        return sizes.size
    }

}