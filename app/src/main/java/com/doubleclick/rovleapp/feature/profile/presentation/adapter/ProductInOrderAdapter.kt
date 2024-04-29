package com.doubleclick.rovleapp.feature.profile.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.databinding.LayoutProductInOrderBinding
import com.doubleclick.rovleapp.feature.profile.data.orders.showOrder.Product

class ProductInOrderAdapter : ListAdapter<Product, ProductInOrderAdapter.ViewHolder>(Differ) {

    internal var clickRateOrder: (String) -> Unit = { _-> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_product_in_order))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutProductInOrderBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickRateOrder)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutProductInOrderBinding,
            product: Product,
            clickRateOrder: (String) -> Unit
        ) {


            binding.product.text = product.product_name


            binding.rateProduct.setOnClickListener {
                clickRateOrder(product.product_id.toString())
            }
        }

    }

    object Differ : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}