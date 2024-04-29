package com.doubleclick.rovleapp.feature.shop.cartDetails.presentation

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.databinding.LayoutPresentationsInOrderBinding
import com.doubleclick.rovleapp.feature.shop.cartDetails.data.orderDetails.CartItem

class OrderItemAdapter : ListAdapter<CartItem, OrderItemAdapter.ViewHolder>(Differ) {
    private lateinit var _binding: LayoutPresentationsInOrderBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.layout_presentations_in_order))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutPresentationsInOrderBinding.bind(holder.itemView)
        _binding = binding
        holder.bind(getItem(position), binding)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val presentationAdapter = OrderPresentationAdapter()
        fun bind(
            item: CartItem,
            binding: LayoutPresentationsInOrderBinding
        ) {
            binding.productName.text = item.product.commercial_name
            binding.rvPresentation.adapter = presentationAdapter
            presentationAdapter.submitList(item.items)

        }
    }

    object Differ : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.items == newItem.items
        }
    }
}