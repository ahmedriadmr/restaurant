package com.doubleclick.restaurant.feature.shop.cart

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.feature.shop.cart.request.putCart.PresentationRequest
import com.doubleclick.restaurant.feature.shop.cart.response.updateCart.request.UpdateCartRequest
import com.doubleclick.restaurant.feature.shop.response.Presentation

class PresentationAdapter : ListAdapter<Presentation, PresentationAdapter.ViewHolder>(Differ) {

    internal var plusListener: (Presentation, UpdateCartRequest?) -> Unit = { _, _ -> }
    internal var minusListener: (Presentation, UpdateCartRequest?) -> Unit = { _, _ -> }
    internal var deleteListener: (Presentation, UpdateCartRequest?) -> Unit = { _, _ -> }
    internal var deleteListenerCart: (Float?) -> Unit = { _ -> }
    private var selectedPresentation: MutableList<PresentationRequest> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_package_in_cart))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(getItem(position), plusListener, minusListener, deleteListener, deleteListenerCart)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weight: TextView = itemView.findViewById(R.id.weight)
        private val totalPricePerItem: TextView = itemView.findViewById(R.id.price)
        private val btnDelete: ImageView = itemView.findViewById(R.id.delete_varity)
        private val btnMinus: ImageView = itemView.findViewById(R.id.mins)
        private val btnPlus: ImageView = itemView.findViewById(R.id.plus)
        private val quantity: TextView = itemView.findViewById(R.id.quantity)
        private val llQuantity: LinearLayout = itemView.findViewById(R.id.ll_quantity)
        private val packages: TextView = itemView.findViewById(R.id.tv_package)
        private val selected: CheckBox = itemView.findViewById(R.id.selected_presentation)
        fun bind(
            item: Presentation,
            plusListener: (Presentation, UpdateCartRequest?) -> Unit,
            minusListener: (Presentation, UpdateCartRequest?) -> Unit,
            deleteListener: (Presentation, UpdateCartRequest?) -> Unit,
            deleteListenerCart: (Float?) -> Unit
        ) {
            val euroSign = "\u20AC"


            weight.text = itemView.context.getString(R.string.weight_placeholder, item.weight)
            totalPricePerItem.text = itemView.context.getString(
                R.string.total_price_per_item,
                item.price_after_discount ?: (item.price_per_unit?.times(item.units)).toString(), euroSign
            )
            packages.text = itemView.context.getString(R.string.units_text, item.units)
            llQuantity.isVisible = true
            quantity.text = item.units.toString()
            selectedPresentation.onEach { if (it.id == item.presentation_id) it.units = item.units }
            when (item.units > 0) {
                true -> {
                    selected.isChecked = true
                    item.product_id?.let {
                        PresentationRequest(
                            item.presentation_id,
                            item.units,
                            it.toInt()
                        )
                    }?.let {
                        selectedPresentation.add(
                            it
                        )
                    }
                }

                false -> {
                    selected.isChecked = false
                    selectedPresentation.removeAll { it.id == item.presentation_id }
                }
            }
            getConfirmedList()
            btnPlus.setOnClickListener {
                item.units++
                btnMinus.isClickable = item.units > 1
                selectedPresentation.onEach { if (it.id == item.presentation_id) it.units = item.units }
                plusListener(item, getConfirmedList())
            }

            btnMinus.setOnClickListener {
                item.units--
                btnMinus.isClickable = item.units > 1
                selectedPresentation.onEach { if (it.id == item.presentation_id) it.units = item.units }
                minusListener(item, getConfirmedList())
            }
            btnMinus.isClickable = item.units > 1
            when (item.units > 0) {
                true -> selected.isChecked = true
                false -> selected.isChecked = false
            }

            btnDelete.setOnClickListener {
                selected.isChecked = false
                selectedPresentation.removeAll { it.id == item.presentation_id }
                deleteListener(item, getConfirmedList())
                if (getConfirmedList() == null) {
                    deleteListenerCart(item.cart_item_id)
                }
            }
        }
    }

    fun getConfirmedList(): UpdateCartRequest? {
        return if (selectedPresentation.isNotEmpty()) {

            UpdateCartRequest(selectedPresentation)
        } else {
            null // Handle the case when selectedPresentation is empty
        }
    }


    object Differ : DiffUtil.ItemCallback<Presentation>() {
        override fun areItemsTheSame(oldItem: Presentation, newItem: Presentation): Boolean {
            return oldItem.presentation_id == newItem.presentation_id
        }

        override fun areContentsTheSame(oldItem: Presentation, newItem: Presentation): Boolean {
            return oldItem == newItem
        }
    }
}
