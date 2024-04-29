package com.doubleclick.rovleapp.feature.profile.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.databinding.LayoutYourPointsOrVisitsBinding
import com.doubleclick.rovleapp.feature.profile.data.points.PointsData

class YourPointsAdapter : ListAdapter<PointsData, YourPointsAdapter.ViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_your_points_or_visits))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutYourPointsOrVisitsBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(binding: LayoutYourPointsOrVisitsBinding, item: PointsData) {

            binding.provider.text = item.provider_name
            binding.userPoints.text = item.points

        }

    }

    object Differ : DiffUtil.ItemCallback<PointsData>() {
        override fun areItemsTheSame(oldItem: PointsData, newItem: PointsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PointsData, newItem: PointsData): Boolean {
            return oldItem == newItem
        }
    }
}