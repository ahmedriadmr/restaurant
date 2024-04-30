package com.doubleclick.restaurant.feature.shop.cartList

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.core.extension.isVisible
import com.doubleclick.restaurant.databinding.LayoutItemCartListBinding
import com.doubleclick.restaurant.feature.shop.cart.response.getCart.NewCart
import com.doubleclick.restaurant.feature.shop.cart.response.updateCart.UpdateCart
import com.doubleclick.restaurant.feature.shop.cart.response.updateCart.request.UpdateCartRequest
import com.doubleclick.restaurant.feature.shop.response.Presentation

class CartListAdapter : ListAdapter<NewCart, CartListAdapter.ViewHolder>(Differ) {
    private lateinit var _binding: LayoutItemCartListBinding
    internal var plusListener: (Int, Presentation, UpdateCartRequest?) -> Unit = {_, _, _ -> }
    internal var minusListener: (Int, Presentation, UpdateCartRequest?) -> Unit = { _,_, _ -> }
    internal var deleteListener: (Int, Float?) -> Unit = { _, _ -> }
    internal var clickOrderNow: (String) -> Unit = { _ -> }
    var updateCart3 : UpdateCart = UpdateCart.empty
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_cart_list))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemCartListBinding.bind(holder.itemView)
        _binding = binding
        holder.bind(
            getItem(position),
            binding,
            plusListener,
            minusListener,
            deleteListener,
            clickOrderNow
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cartListItemAdapter = CartListItemAdapter()
        fun bind(
            item: NewCart,
            binding: LayoutItemCartListBinding,
            plusListener: (Int, Presentation, UpdateCartRequest?) -> Unit,
            minusListener: (Int, Presentation, UpdateCartRequest?) -> Unit,
            deleteListener: (Int, Float?) -> Unit,
            clickOrderNow: (String) -> Unit
        ) {
            binding.providerName.text = item.commercial_name
            val commercialNames = item.cart_items.joinToString(", ") { it.product?.commercial_name ?: "" }
            binding.productsNames.text = commercialNames
            binding.rvMyPackage.adapter = cartListItemAdapter
            cartListItemAdapter.submitList(item.cart_items)
            cartListItemAdapter.plusListener = {id , request-> plusListener(bindingAdapterPosition,id, request) }
            cartListItemAdapter.minusListener = {id , request-> minusListener(bindingAdapterPosition,id, request) }
            cartListItemAdapter.deleteListener = { cardId -> deleteListener(bindingAdapterPosition, cardId) }
            cartListItemAdapter.updateCart2 = updateCart3

            binding.showMoreDetails.setOnClickListener {
                when (binding.rvMyPackage.isVisible()) {
                    true -> {
                        binding.rvMyPackage.visibility = View.GONE
                        binding.arrowIndicator.rotation = 0f
                    }

                    false -> {
                        binding.rvMyPackage.visibility = View.VISIBLE
                        binding.arrowIndicator.rotation = 180f
                    }
                }
            }

            binding.orderNow.setOnClickListener {
                clickOrderNow(item.id)
            }
        }
    }


    object Differ : DiffUtil.ItemCallback<NewCart>() {
        override fun areItemsTheSame(oldItem: NewCart, newItem: NewCart): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewCart, newItem: NewCart): Boolean {
            return oldItem == newItem
        }
    }
}