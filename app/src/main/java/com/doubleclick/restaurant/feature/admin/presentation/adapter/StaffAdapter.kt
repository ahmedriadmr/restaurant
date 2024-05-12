package com.doubleclick.restaurant.feature.admin.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemStaffBinding
import com.doubleclick.restaurant.feature.admin.data.listStaff.UsersData

class StaffAdapter : ListAdapter<UsersData, StaffAdapter.ViewHolder>(Differ) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_item_staff))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemStaffBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position))

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            binding: LayoutItemStaffBinding,
            item: UsersData
        ) {

            binding.name.text = "${item.first_name} ${item.last_name}"
            binding.role.text = item.roles.firstOrNull()?.name
            when(item.roles.firstOrNull()?.name){
                "waiter" -> binding.role.setTextColor(ContextCompat.getColor(itemView.context, R.color.purple))
                "chief" -> binding.role.setTextColor(ContextCompat.getColor(itemView.context, R.color.yellow_dark))
            }


        }

    }




    object Differ : DiffUtil.ItemCallback<UsersData>() {
        override fun areItemsTheSame(oldItem: UsersData, newItem: UsersData): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: UsersData, newItem: UsersData): Boolean {
            return oldItem == newItem
        }
    }
}