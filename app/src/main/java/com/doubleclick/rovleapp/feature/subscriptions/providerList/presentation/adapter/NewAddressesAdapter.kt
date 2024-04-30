package com.doubleclick.restaurant.feature.subscriptions.providerList.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemCountryProvinceCityBinding
import com.doubleclick.restaurant.feature.profile.data.listAddresses.AddressesData

class NewAddressesAdapter : ListAdapter<AddressesData, NewAddressesAdapter.ViewHolder>(Differ) {
    var selectedLocation: AddressesData? = null

    internal var clickListenerEditAddresses: () -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_country_province_city))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemCountryProvinceCityBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickListenerEditAddresses)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemCountryProvinceCityBinding,
            item: AddressesData,
            clickListenerEditAddresses: () -> Unit = {}
        ) {
            binding.name.text = item.address

            binding.selected.isChecked = item.isSelected // Set the initial checked state

            binding.selected.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedLocation = item
                    // Uncheck all other items
                    currentList.forEach { it.isSelected = (it == item) }
                    itemView.post {
                        notifyDataSetChanged() // Notify adapter to update views
                        clickListenerEditAddresses()
                    }
                }
            }
        }
    }

    object Differ : DiffUtil.ItemCallback<AddressesData>() {
        override fun areItemsTheSame(oldItem: AddressesData, newItem: AddressesData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AddressesData, newItem: AddressesData): Boolean {
            return oldItem == newItem
        }
    }
}
