package com.doubleclick.rovleapp.feature.shop.cartDetails.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.databinding.LayoutItemCountryProvinceCityBinding
import com.doubleclick.rovleapp.feature.shop.cartDetails.data.filterCities.Province

class NewFilterProvinceAdapter : ListAdapter<Province, NewFilterProvinceAdapter.ViewHolder>(Differ) {
    internal var clickListenerEditProvince: () -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_country_province_city))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemCountryProvinceCityBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position),clickListenerEditProvince)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemCountryProvinceCityBinding,
            item: Province,
            clickListenerEditProvince: () -> Unit = {}
        ) {


            binding.selected.setOnClickListener {
                    // Uncheck all other items
                    currentList.forEach { it.isSelected = false }
                    item.isSelected = true
                        notifyDataSetChanged() // Notify adapter to update views
                        clickListenerEditProvince()


            }
            binding.name.text = item.name

            binding.selected.isChecked = item.isSelected // Set the initial checked state
        }
    }

    object Differ : DiffUtil.ItemCallback<Province>() {
        override fun areItemsTheSame(oldItem: Province, newItem: Province): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Province, newItem: Province): Boolean {
            return oldItem == newItem
        }
    }
}
