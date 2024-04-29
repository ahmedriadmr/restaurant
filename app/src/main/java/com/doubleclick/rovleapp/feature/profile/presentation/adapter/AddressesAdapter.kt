package com.doubleclick.rovleapp.feature.profile.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.databinding.LayoutYourAddressesBinding
import com.doubleclick.rovleapp.feature.profile.data.listAddresses.AddressesData

class AddressesAdapter : ListAdapter<AddressesData, AddressesAdapter.ViewHolder>(Differ) {

    internal var clickListenerEdit: (String) -> Unit = {_-> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_your_addresses))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutYourAddressesBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickListenerEdit)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(binding: LayoutYourAddressesBinding, item: AddressesData, clickListenerEdit: (String) -> Unit) {
            binding.address.text = item.address
            binding.edit.setOnClickListener {
                clickListenerEdit(item.id.toString())
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