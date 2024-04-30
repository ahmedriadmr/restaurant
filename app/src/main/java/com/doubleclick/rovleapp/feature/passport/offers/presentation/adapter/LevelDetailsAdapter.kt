package com.doubleclick.restaurant.feature.passport.offers.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemLevelBinding
import com.doubleclick.restaurant.feature.passport.offers.data.Level

class LevelDetailsAdapter : ListAdapter<Level, LevelDetailsAdapter.ViewHolder>(Differ) {

    internal var clickListenerApp: (String, String) -> Unit = {_,_-> }
    internal var clickListenerShop: () -> Unit = { }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_level))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemLevelBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickListenerApp, clickListenerShop)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(binding: LayoutItemLevelBinding, item: Level, clickListenerApp: (String, String) -> Unit, clickListenerShop: () -> Unit) {
            binding.discount.text = item.description
            binding.appAvailability.setOnClickListener {
                clickListenerApp(item.id , item.discount)
            }
            binding.shopAvailability.setOnClickListener {
                clickListenerShop()
            }
            when (item.offer_place) {
                "auto" -> {
                    binding.appAvailability.setImageResource(R.drawable.cart_in_offers)
                    binding.shopAvailability.setImageResource(R.drawable.shop_icon2)
                    binding.appAvailability.isClickable = true
                    binding.shopAvailability.isClickable = true
                }

                "app" -> {
                    binding.appAvailability.setImageResource(R.drawable.cart_in_offers)
                    binding.shopAvailability.setImageResource(R.drawable.shop_icon_notactivated)
                    binding.appAvailability.isClickable = true
                    binding.shopAvailability.isClickable = false
                }

                "coffeeShop" -> {
                    binding.appAvailability.setImageResource(R.drawable.cart_icon_notactivated)
                    binding.shopAvailability.setImageResource(R.drawable.shop_icon2)
                    binding.appAvailability.isClickable = false
                    binding.shopAvailability.isClickable = true
                }

                else -> {
                    binding.appAvailability.setImageResource(R.drawable.cart_icon_notactivated)
                    binding.shopAvailability.setImageResource(R.drawable.shop_icon_notactivated)
                }

            }
        }


    }

    object Differ : DiffUtil.ItemCallback<Level>() {
        override fun areItemsTheSame(oldItem: Level, newItem: Level): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Level, newItem: Level): Boolean {
            return oldItem == newItem
        }
    }
}