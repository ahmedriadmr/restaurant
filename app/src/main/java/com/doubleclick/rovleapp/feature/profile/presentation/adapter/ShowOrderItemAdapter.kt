package com.doubleclick.restaurant.feature.profile.presentation.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutPresentationsInOrderBinding
import com.doubleclick.restaurant.feature.profile.data.orders.showOrder.Product

class ShowOrderItemAdapter : ListAdapter<Product, ShowOrderItemAdapter.ViewHolder>(Differ) {
    private lateinit var _binding: LayoutPresentationsInOrderBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.layout_presentations_in_order))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutPresentationsInOrderBinding.bind(holder.itemView)
        _binding = binding
        holder.bind(getItem(position), binding)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val presentationAdapter = ShowOrderPresentationAdapter()
        fun bind(
            item: Product,
            binding: LayoutPresentationsInOrderBinding
        ) {
            binding.productName.text = item.product_name
            binding.rvPresentation.adapter = presentationAdapter
            presentationAdapter.submitList(item.items)

        }
    }

    object Differ : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.items == newItem.items
        }
    }
}