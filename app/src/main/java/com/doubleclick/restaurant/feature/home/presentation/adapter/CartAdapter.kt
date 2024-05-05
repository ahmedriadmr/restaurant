package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.formatted
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.ItemCartBinding
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.utils.Constant
import com.doubleclick.restaurant.utils.Constant.dollarSign

class CartAdapter: ListAdapter<CartData, CartAdapter.ViewHolder>(Differ) {

    internal var clickUpdateCart: (String,String,String) -> Unit = {_,_, _ -> }
    internal var cartUpdated: (String) -> Unit = { _ -> }
    private var totalCartPrice: Double = 0.0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_cart))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = ItemCartBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickUpdateCart)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            binding: ItemCartBinding,
            cart: CartData,
            clickUpdateCart: (String,String,String) -> Unit
        ) {

            binding.name.text = cart.size.item.name
            binding.size.text = cart.size.name
            binding.price.text = "$dollarSign${cart.size.price}"
            binding.quantity.text = cart.number.toString()
            calculateTotalPrice(currentList)
            if (!cart.size.item.image.isNullOrEmpty()) {
                binding.image.load(Constant.BASE_URL_IMAGE_ITEMS + cart.size.item.image) {
                    crossfade(true)
                    placeholder(R.drawable.image)
                    error(R.drawable.image)
                }
            }
            binding.plus.setOnClickListener {
                val currentQuantity = binding.quantity.text.toString().toInt()
                val newQuantity = currentQuantity + 1
                binding.quantity.text = newQuantity.toString()
                clickUpdateCart(cart.id, newQuantity.toString(), cart.size_id)
                calculateTotalPrice(currentList)
            }

            binding.minus.setOnClickListener {
                val currentQuantity = binding.quantity.text.toString().toInt()
                if (currentQuantity > 1) {
                    val newQuantity = currentQuantity - 1
                    binding.quantity.text = newQuantity.toString()
                    clickUpdateCart(cart.id, newQuantity.toString(), cart.size_id)
                }
                calculateTotalPrice(currentList)
            }





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