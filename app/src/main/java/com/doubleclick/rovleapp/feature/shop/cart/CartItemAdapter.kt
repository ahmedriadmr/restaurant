package com.doubleclick.restaurant.feature.shop.cart

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.core.extension.isVisible
import com.doubleclick.restaurant.databinding.LayoutItemsInProviderCartBinding
import com.doubleclick.restaurant.feature.shop.cart.response.getCart.CartItem
import com.doubleclick.restaurant.feature.shop.cart.response.updateCart.UpdateCart
import com.doubleclick.restaurant.feature.shop.cart.response.updateCart.request.UpdateCartRequest
import com.doubleclick.restaurant.feature.shop.response.Presentation

class CartItemAdapter : ListAdapter<CartItem, CartItemAdapter.ViewHolder>(Differ) {
    private lateinit var _binding: LayoutItemsInProviderCartBinding
    internal var plusListener: (Int, Presentation, UpdateCartRequest?) -> Unit = { _, _, _ -> }
    internal var minusListener: (Int, Presentation, UpdateCartRequest?) -> Unit = { _, _, _ -> }
    internal var deleteListener: (Int, Presentation, UpdateCartRequest?) -> Unit = { _, _, _ -> }
    internal var deleteListenerCart: (Int, Float?) -> Unit = { _, _ -> }
    var updateCart: UpdateCart = UpdateCart.empty
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.layout_items_in_provider_cart))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemsInProviderCartBinding.bind(holder.itemView)
        _binding = binding
        holder.bind(getItem(position), binding, plusListener, minusListener, deleteListener, deleteListenerCart)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val presentationAdapter = PresentationAdapter()
        fun bind(
            item: CartItem,
            binding: LayoutItemsInProviderCartBinding,
            plusListener: (Int, Presentation, UpdateCartRequest?) -> Unit,
            minusListener: (Int, Presentation, UpdateCartRequest?) -> Unit,
            deleteListener: (Int, Presentation, UpdateCartRequest?) -> Unit,
            deleteListenerCart: (Int, Float?) -> Unit
        ) {
            binding.productName.text = item.product?.commercial_name
            binding.rvPresentation.adapter = presentationAdapter
            when (item.id == updateCart.id) {
                true -> item.presentations = updateCart.presentations
                else -> item.presentations = item.presentations
            }
            presentationAdapter.submitList(item.presentations)
            presentationAdapter.plusListener = { id, request -> plusListener(bindingAdapterPosition, id, request) }
            presentationAdapter.minusListener = { id, request -> minusListener(bindingAdapterPosition, id, request) }
            presentationAdapter.deleteListener = { id, request -> deleteListener(bindingAdapterPosition, id, request) }
            presentationAdapter.deleteListenerCart = { id -> deleteListenerCart(bindingAdapterPosition, id) }

            if (item.isOpened) {
                binding.rvPresentation.visibility = View.VISIBLE
                binding.arrow.rotation = 0f
            } else {
                binding.rvPresentation.visibility = View.GONE
                binding.arrow.rotation = -90f
            }

            binding.llProduct.setOnClickListener {
                when (binding.rvPresentation.isVisible()) {
                    true -> {
                        binding.rvPresentation.visibility = View.GONE
                        binding.arrow.rotation = -90f
                        item.isOpened = false
                    }

                    false -> {
                        binding.rvPresentation.visibility = View.VISIBLE
                        binding.arrow.rotation = 0f
                        item.isOpened = true
                    }
                }
            }
        }
    }

    object Differ : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }
}