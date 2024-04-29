package com.doubleclick.rovleapp.feature.shop.cartDetails.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.databinding.LayoutItemCountryProvinceCityBinding
import com.doubleclick.rovleapp.feature.shop.cartDetails.data.filterCities.City

class NewFilterCityAdapter : ListAdapter<City, NewFilterCityAdapter.ViewHolder>(Differ) {
    internal var clickListenerEditCity: () -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_country_province_city))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemCountryProvinceCityBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position),clickListenerEditCity)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemCountryProvinceCityBinding,
            item: City,
            clickListenerEditCity: () -> Unit = {}
        ) {


            binding.selected.setOnClickListener {
                currentList.forEach { it.isSelected = false }
                item.isSelected = true
                notifyDataSetChanged()
                clickListenerEditCity()
            }

            binding.selected.isChecked = item.isSelected

            binding.name.text = item.name

        }
    }

    object Differ : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }
}
