package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.formatted
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemOrderItemBinding
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.feature.home.data.makeOrder.request.ItemRequest
import com.doubleclick.restaurant.utils.Constant


class OrderItemsAdapter: ListAdapter<CartData, OrderItemsAdapter.ViewHolder>(Differ) {

    internal var cartUpdated: (String) -> Unit = { _ -> }
    private var totalCartPrice: Double = 0.0
    private var selectedPresentation: MutableList<ItemRequest>? = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_item_order_item))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = com.doubleclick.restaurant.databinding.LayoutItemOrderItemBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position))

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            binding: LayoutItemOrderItemBinding,
            cart: CartData
        ) {

            binding.name.text = cart.size.item.name
            binding.size.text = cart.size.name
            binding.count.text = "X${cart.number}"
            binding.price.text = "${Constant.dollarSign}${cart.size.price * cart.number}"

            calculateTotalPrice(currentList)
            ItemRequest(
                cart.size.item_id,
                cart.number,
                cart.size.name,
                cart.size.price,
                cart.size.price * cart.number
            ).let {
                selectedPresentation?.add(it)
            }

        }

    }

    fun getConfirmedList(): List<ItemRequest>? {
        return if (!selectedPresentation.isNullOrEmpty()) {

            selectedPresentation
        } else {
            null // Handle the case when selectedPresentation is empty
        }
    }
    private fun calculateTotalPrice(cartItems: List<CartData>) {
        totalCartPrice = 0.0
        for (item in cartItems) {
            totalCartPrice += item.size.price * item.number
        }
        cartUpdated(totalCartPrice.formatted)
    }

    object Differ : DiffUtil.ItemCallback<CartData>() {
        override fun areItemsTheSame(oldItem: CartData, newItem: CartData): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: CartData, newItem: CartData): Boolean {
            return oldItem == newItem
        }
    }
}