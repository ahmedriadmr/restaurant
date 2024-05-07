package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemDishInMenuBinding
import com.doubleclick.restaurant.feature.home.data.Categories.Item
import com.doubleclick.restaurant.utils.Constant
import com.doubleclick.restaurant.utils.Constant.dollarSign

class RestaurantDishAdapter: ListAdapter<Item, RestaurantDishAdapter.ViewHolder>(Differ) {

    internal var clickShowItem: (String,String) -> Unit = {_, _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_item_dish_in_menu))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemDishInMenuBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickShowItem)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sizeDishAdapter = SizesDishAdapter()
        fun bind(
            binding: LayoutItemDishInMenuBinding,
            item: Item,
            clickShowItem: (String,String) -> Unit
        ) {
            binding.rvSize.adapter = sizeDishAdapter
            sizeDishAdapter.submitList(item.sizes)
            binding.nameFood.text = item.name
            binding.nameDes.text = item.description
            if (!item.image.isNullOrEmpty()) {
                binding.imageFood.load(Constant.BASE_URL_IMAGE_ITEMS + item.image) {
                    crossfade(true)
                    placeholder(R.drawable.image)
                    error(R.drawable.image)
                }
            }
            sizeDishAdapter.clickShowSize = { id , price ->
                binding.price.text = "$dollarSign${price}"
                binding.addToCart.setOnClickListener {
                    clickShowItem(id , price.toString())
                }
            }


        }


    }

    object Differ : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}