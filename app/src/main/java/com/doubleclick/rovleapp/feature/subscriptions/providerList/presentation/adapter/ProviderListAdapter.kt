package com.doubleclick.restaurant.feature.subscriptions.providerList.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemSubscriptionBinding
import com.doubleclick.restaurant.feature.subscriptions.providerList.data.listPlans.PlansData

class ProviderListAdapter : ListAdapter<PlansData, ProviderListAdapter.ViewHolder>(Differ) {

    internal var clickPlan: (String) -> Unit = { _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_subscription))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemSubscriptionBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickPlan)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemSubscriptionBinding,
            item: PlansData,
            clickPlan: (String) -> Unit
        ) {
            binding.planName.text = item.name
            binding.providerName.text = item.provider.commercial_name
            // Check if description has more than 4 words
            val truncatedDescription = if (item.description.split(" ").size > 3) {
                // Truncate and append three dots
                item.description.split(" ").take(3).joinToString(" ") + "..."
            } else {
                item.description
            }
            binding.description.text = truncatedDescription

            binding.btnSubscription.setOnClickListener {
                clickPlan(item.id)
            }
        }
    }

    object Differ : DiffUtil.ItemCallback<PlansData>() {
        override fun areItemsTheSame(oldItem: PlansData, newItem: PlansData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlansData, newItem: PlansData): Boolean {
            return oldItem == newItem
        }
    }
}