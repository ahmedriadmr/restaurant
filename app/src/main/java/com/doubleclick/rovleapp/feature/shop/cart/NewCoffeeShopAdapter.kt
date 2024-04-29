package com.doubleclick.rovleapp.feature.shop.cart

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.databinding.LayoutItemChooseOneBinding
import com.doubleclick.rovleapp.feature.shop.response.CoffeeShop

class NewCoffeeShopAdapter : ListAdapter<CoffeeShop, NewCoffeeShopAdapter.ViewHolder>(Differ) {

    internal var clickListenerEditCoffeeShop: () -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_choose_one))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemChooseOneBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickListenerEditCoffeeShop)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemChooseOneBinding,
            item: CoffeeShop,
            clickListenerEditCoffeeShop: () -> Unit = {}
        ) {
            binding.nameCoffee.text = item.name
            binding.addressCoffee.text = item.address

            binding.selected.isChecked = item.isSelected // Set the initial checked state

            binding.selected.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Uncheck all other items
                    currentList.forEach { it.isSelected = (it == item) }
                    itemView.post {
                        notifyDataSetChanged() // Notify adapter to update views
                        clickListenerEditCoffeeShop()
                    }
                }
            }
        }
    }

    object Differ : DiffUtil.ItemCallback<CoffeeShop>() {
        override fun areItemsTheSame(oldItem: CoffeeShop, newItem: CoffeeShop): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoffeeShop, newItem: CoffeeShop): Boolean {
            return oldItem == newItem
        }
    }
}
