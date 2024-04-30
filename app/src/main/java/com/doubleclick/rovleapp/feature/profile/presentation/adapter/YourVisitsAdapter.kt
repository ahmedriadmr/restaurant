package com.doubleclick.restaurant.feature.profile.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutYourPointsOrVisitsBinding
import com.doubleclick.restaurant.feature.profile.data.visits.VisitsData

class YourVisitsAdapter : ListAdapter<VisitsData, YourVisitsAdapter.ViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_your_points_or_visits))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutYourPointsOrVisitsBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(binding: LayoutYourPointsOrVisitsBinding, item: VisitsData) {

            binding.provider.text = item.provider_name
            binding.userPoints.text = item.visits

        }

    }

    object Differ : DiffUtil.ItemCallback<VisitsData>() {
        override fun areItemsTheSame(oldItem: VisitsData, newItem: VisitsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VisitsData, newItem: VisitsData): Boolean {
            return oldItem == newItem
        }
    }
}