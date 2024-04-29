package com.doubleclick.rovleapp.feature.shop.cart

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.databinding.LayoutItemChooseOneBinding
import com.doubleclick.rovleapp.feature.shop.cart.locker.data.LockerData

class NewLockerAdapter : ListAdapter<LockerData, NewLockerAdapter.ViewHolder>(Differ) {

    internal var clickListenerEditLocker: () -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_choose_one))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemChooseOneBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position),clickListenerEditLocker)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemChooseOneBinding,
            item: LockerData,
            clickListenerEditLocker: () -> Unit = {}
        ) {
            binding.nameCoffee.text = item.det_canal
            binding.addressCoffee.text = item.direccion

            binding.selected.isChecked = item.isSelected // Set the initial checked state

            binding.selected.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Uncheck all other items
                    currentList.forEach { it.isSelected = (it == item) }
                    itemView.post { // Post a Runnable to execute notifyDataSetChanged outside of RecyclerView's layout computation
                        notifyDataSetChanged() // Notify adapter to update views
                        clickListenerEditLocker()
                    }
                }
            }
        }
    }

    object Differ : DiffUtil.ItemCallback<LockerData>() {
        override fun areItemsTheSame(oldItem: LockerData, newItem: LockerData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LockerData, newItem: LockerData): Boolean {
            return oldItem == newItem
        }
    }
}
