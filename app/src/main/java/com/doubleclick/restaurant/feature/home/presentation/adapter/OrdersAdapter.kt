package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.feature.home.data.searchOrders.response.SearchOrdersData
import com.doubleclick.restaurant.utils.Constant

class OrdersAdapter : ListAdapter<SearchOrdersData, OrdersAdapter.ViewHolder>(Differ) {
    internal var clickCancelOrder: (String) -> Unit = { _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_item_my_orders))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = com.doubleclick.restaurant.databinding.LayoutItemMyOrdersBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position),clickCancelOrder)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemsInOrderAdapter = ItemsInOrderAdapter()
        fun bind(
            binding: com.doubleclick.restaurant.databinding.LayoutItemMyOrdersBinding,
            order: SearchOrdersData,
            clickCancelOrder: (String) -> Unit
        ) {

            binding.date.text = order.created_at
            binding.total.text = "${Constant.dollarSign}${order.total}"
            binding.place.text = order.order_type
            binding.status.text = order.status
            binding.rvMyOldOrders.adapter = itemsInOrderAdapter
            itemsInOrderAdapter.submitList(order.items)
            when(order.status){
                "Ongoing" -> {
                    binding.llStatus.setBackgroundResource(R.drawable.bg_yellow_rec_fill_r15)
                    binding.cancel.visibility = View.VISIBLE
                }
                "Canceled" ->  {
                    binding.llStatus.setBackgroundResource(R.drawable.bg_red_rec_fill_r15)
                    binding.cancel.visibility = View.GONE
                }
                "Received" -> {
                    binding.llStatus.setBackgroundResource(R.drawable.bg_green_rec_fill_r15)
                    binding.cancel.visibility = View.GONE
                }
                "Done" -> {
                    binding.llStatus.setBackgroundResource(R.drawable.bg_gray_rec_fill_r15)
                    binding.cancel.visibility = View.GONE
                }
            }

            binding.arrow.setOnClickListener {
                if(binding.llOldOrder.visibility == View.VISIBLE){
                    binding.llOldOrder.visibility = View.GONE
                    binding.arrow.rotation = 90f
                } else {
                    binding.llOldOrder.visibility = View.VISIBLE
                    binding.arrow.rotation = -90f
                }
            }

            binding.cancel.setOnClickListener {
                clickCancelOrder(order.id.toString())
            }


        }

    }


    object Differ : DiffUtil.ItemCallback<SearchOrdersData>() {
        override fun areItemsTheSame(oldItem: SearchOrdersData, newItem: SearchOrdersData): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: SearchOrdersData, newItem: SearchOrdersData): Boolean {
            return oldItem == newItem
        }
    }
}