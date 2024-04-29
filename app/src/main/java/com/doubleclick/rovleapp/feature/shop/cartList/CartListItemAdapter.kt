package com.doubleclick.rovleapp.feature.shop.cartList

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.core.extension.isVisible
import com.doubleclick.rovleapp.databinding.LayoutItemsInListCartBinding
import com.doubleclick.rovleapp.feature.shop.cart.response.getCart.CartItem
import com.doubleclick.rovleapp.feature.shop.cart.response.updateCart.UpdateCart
import com.doubleclick.rovleapp.feature.shop.cart.response.updateCart.request.UpdateCartRequest
import com.doubleclick.rovleapp.feature.shop.response.Presentation

class CartListItemAdapter : ListAdapter<CartItem, CartListItemAdapter.ViewHolder>(Differ) {
    private lateinit var _binding: LayoutItemsInListCartBinding
    internal var plusListener: (Presentation, UpdateCartRequest?) -> Unit = {_, _ -> }
    internal var minusListener: (Presentation, UpdateCartRequest?) -> Unit = {_, _ -> }
    internal var deleteListener: (Float?) -> Unit = {  _ -> }
    var updateCart2 : UpdateCart = UpdateCart.empty
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.layout_items_in_list_cart))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemsInListCartBinding.bind(holder.itemView)
        _binding = binding
        holder.bind(getItem(position), binding, plusListener, minusListener, deleteListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val presentationAdapter = PresentationListAdapter()
        fun bind(
            item: CartItem,
            binding: LayoutItemsInListCartBinding,
            plusListener: (Presentation, UpdateCartRequest?) -> Unit,
            minusListener: (Presentation, UpdateCartRequest?) -> Unit,
            deleteListener: ( Float?) -> Unit) {
            binding.productName.text = item.product?.commercial_name
            binding.rvPresentation.adapter = presentationAdapter
            when(item.id == updateCart2.id){
                true -> item.presentations = updateCart2.presentations
                else -> item.presentations = item.presentations
            }
            presentationAdapter.submitList(item.presentations)
            presentationAdapter.plusListener = {id , request-> plusListener(id,request ) }
            presentationAdapter.minusListener = {  id , request-> minusListener(id,request ) }
            binding.deleteVarity.setOnClickListener {
                deleteListener(item.id.toFloat())
            }

            if (item.isOpened) {
                binding.rvPresentation.visibility = View.VISIBLE
                binding.arrow.rotation = 0f
            } else {
                binding.rvPresentation.visibility = View.GONE
                binding.arrow.rotation = -90f
            }
            binding.llPackage.setOnClickListener {
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
            return oldItem.product_id == newItem.product_id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }
}