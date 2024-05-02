package com.doubleclick.restaurant.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.cascade.CascadePopupMenu
import com.doubleclick.domain.model.items.get.Item
import com.doubleclick.restaurant.viewHolder.DishRowItemViewHolder
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.utils.Constant.BASE_URL_IMAGE_ITEMS

class DishRowItemAdapter(val items: List<Item>) : RecyclerView.Adapter<DishRowItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishRowItemViewHolder =
        DishRowItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_row_dish, parent, false)
        )

    override fun onBindViewHolder(holder: DishRowItemViewHolder, position: Int) {
        holder.category.text = items[holder.absoluteAdapterPosition].category.name
        holder.name.text = items[holder.absoluteAdapterPosition].name
        holder.type.text =
            if (items[holder.absoluteAdapterPosition].vip.toInt() == 1) holder.itemView.context.getString(
                R.string.vip
            ) else holder.itemView.context.getString(R.string.n)
        holder.type.background =
            if (items[holder.absoluteAdapterPosition].vip.toInt() == 1) ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.bg_yellow_circle
            ) else ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_gray_circle)
        holder.circleImageView.borderColor =
            if (items[holder.absoluteAdapterPosition].vip.toInt() == 1) ContextCompat.getColor(
                holder.itemView.context,
                R.color.yellow
            ) else ContextCompat.getColor(holder.itemView.context, R.color.gray)
        Glide.with(holder.itemView.context)
            .load(BASE_URL_IMAGE_ITEMS + items[holder.absoluteAdapterPosition].image)
            .into(holder.circleImageView)
        holder.option.setOnClickListener {
            showCascadeMenu(anchor = it, holder.itemView.context)
        }
    }

    private fun showCascadeMenu(anchor: View, context: Context) {
        val popupMenu = CascadePopupMenu(context, anchor, styler = cascadeMenuStyler(context))
        popupMenu.menu.apply {
            MenuCompat.setGroupDividerEnabled(this, true)
            add("Edit").setIcon(R.drawable.edit).setOnMenuItemClickListener {
                Toast.makeText(context,"Edte",Toast.LENGTH_SHORT).show()
                true
            }
            add("Delete").setIcon(R.drawable.delete).setOnMenuItemClickListener {
                Toast.makeText(context,"delete",Toast.LENGTH_SHORT).show()
                true
            }
        }
        popupMenu.show()
    }

    private fun cascadeMenuStyler(context: Context): CascadePopupMenu.Styler {
        val rippleDrawable = {
            RippleDrawable(
                ColorStateList.valueOf(Color.parseColor("#B1DDC6")), null, ColorDrawable(
                    Color.BLACK
                )
            )
        }
        return CascadePopupMenu.Styler(
            background = {
                ContextCompat.getDrawable(context, R.drawable.bg_white)
            },
            menuTitle = {
                it.titleView.typeface = ResourcesCompat.getFont(context, R.font.roboto)
                it.setBackground(rippleDrawable())
            },
            menuItem = {
                it.titleView.typeface = ResourcesCompat.getFont(context, R.font.roboto)
                it.setBackground(rippleDrawable())
                it.setGroupDividerColor(Color.parseColor("#BED9CF"))
            }
        )
    }

    override fun getItemCount(): Int = items.size


}