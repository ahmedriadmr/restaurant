package com.doubleclick.rovleapp.feature.shop


import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.feature.shop.response.Product


class ShopAdapter(private val context: Activity) : ListAdapter<Product, ShopAdapter.ViewHolder>(Differ) {

    internal var clickListenerCart: (String) -> Unit = { _ -> }
    internal var clickListenerDetails: (String) -> Unit = { _ -> }
    internal var clickListener: (String) -> Unit = { _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_shop))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(getItem(position), clickListenerCart, clickListenerDetails, clickListener)


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val discount = itemView.findViewById<TextView>(R.id.offerDiscount)
        private val roastedBy = itemView.findViewById<TextView>(R.id.roasted_by)
        private val sca = itemView.findViewById<TextView>(R.id.sca)
        private val cart = itemView.findViewById<LinearLayout>(R.id.go_to_cart)
        private val verFicha = itemView.findViewById<TextView>(R.id.more_info)
        fun bind(product: Product, clickListenerCart: (String) -> Unit, clickListenerDetails: (String) -> Unit, clickListener: (String) -> Unit) {
            name.text = product.commercial_name
            roastedBy.text = product.provider?.commercial_name
            sca.text = itemView.context.getString(R.string.scascore, product.sca_score.toString())
            val discountValue = (context as ShopActivity).intent?.getStringExtra("discount")
            discount.text = discountValue?.let { "Descuento: $it%" } ?: ""

            val discountShow = context.intent?.getStringExtra("providerId")
            if (product.provider_id == discountShow) {
                discount.visibility = View.VISIBLE
            } else {
                discount.visibility = View.GONE
            }

            cart.setOnClickListener {
                clickListenerCart(product.id)
            }
            itemView.setOnClickListener {
                clickListener(product.id)
            }
            verFicha.setOnClickListener {
                clickListenerDetails(product.id)
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
