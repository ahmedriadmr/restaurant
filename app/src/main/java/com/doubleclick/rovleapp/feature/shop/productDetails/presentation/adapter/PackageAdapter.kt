package com.doubleclick.restaurant.feature.shop.productDetails.presentation.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.formatted
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.feature.shop.cart.request.putCart.PresentationRequest
import com.doubleclick.restaurant.feature.shop.cart.request.putCart.PutCartRequest
import com.doubleclick.restaurant.feature.shop.cart.response.getCart.CartItem
import com.doubleclick.restaurant.feature.shop.response.Presentation
import com.doubleclick.restaurant.feature.shop.showOffer.data.ShowOfferData
import com.doubleclick.restaurant.utils.Constant.euroSign
import kotlin.properties.Delegates

class PackageAdapter : RecyclerView.Adapter<PackageAdapter.ViewHolder>() {

    internal var showProductCollection: List<Presentation> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
        calculateTotalPrice()
    }

    private var cartData: CartItem? = null
    fun setCart(data: CartItem?) {
        cartData = data
    }

    private var showOfferData: ShowOfferData? = null

    fun implementShowOfferData(data: ShowOfferData?) {
        showOfferData = data
        notifyDataSetChanged()
    }

    internal var cartUpdated: (String) -> Unit = { _ -> }
    private var totalCartPrice: Double = 0.0
    private var selectedPresentation: MutableList<PresentationRequest> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_variety))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = showProductCollection[position]
        findPresentationInShowOffer(item.id)?.let { offer -> item.price = offer.after_dicount_price
        }


        holder.weight.text = holder.itemView.context.getString(R.string.weight_placeholder, item.weight)
        holder.price.text = holder.itemView.context.getString(R.string.total_cost_placeholder, item.price.toString(), euroSign)
        holder.quantity.text = item.units.toString()


        val cartPresentation = cartData?.presentations?.find { it.presentation_id == item.id }

        if (cartPresentation != null) {
            // Presentation ID exists in the cart, set as selected with its units
            holder.selected.isChecked = true
            holder.quantity.text = cartPresentation.units.toString()
            item.units = cartPresentation.units
            item.product_id?.let {
                PresentationRequest(
                    item.id,
                    item.units,
                    it.toInt()
                )
            }?.let {
                selectedPresentation.add(
                    it
                )
            }
            calculateTotalPrice()
        } else {
            // Presentation ID doesn't exist in the cart, set as not selected
            holder.selected.isChecked = false
            holder.quantity.text = "0"
            item.units = 0
            selectedPresentation.removeAll { it.id == item.id }
        }
//        holder.quantity.text = 1.toString()
//item.units = 1
        holder.btnPlus.setOnClickListener {
            item.units++
            holder.quantity.text = item.units.toString()
            holder.btnMinus.isClickable = item.units > 0
            calculateTotalPrice()
            selectedPresentation.onEach { if (it.id == item.id) it.units = item.units }
            when (item.units > 0){
                true -> holder.selected.isChecked = true
                false -> holder.selected.isChecked = false
            }
        }

        holder.btnMinus.setOnClickListener {
            item.units--
            holder.quantity.text = item.units.toString()
            holder.btnMinus.isClickable = item.units > 0
            calculateTotalPrice()
            selectedPresentation.onEach { if (it.id == item.id) it.units = item.units }
            when (item.units > 0){
                true -> holder.selected.isChecked = true
                false -> holder.selected.isChecked = false
            }
        }

        holder.btnMinus.isClickable = item.units > 0

        when (item.units > 0){
            true -> holder.selected.isChecked = true
            false -> holder.selected.isChecked = false
        }
        holder.selected.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
            calculateTotalPrice()
            when (isChecked) {
                true -> item.product_id?.let {
                    PresentationRequest(
                        item.id,
                        item.units,
                        it.toInt()
                    )
                }?.let {
                    selectedPresentation.add(
                        it
                    )
                }

                false -> selectedPresentation.removeAll { it.id == item.id }
            }
        }
    }

    //     Function to get the confirmed cart list with null check
    fun getConfirmedList(): PutCartRequest? {
        return if (selectedPresentation.isNotEmpty()) {

            PutCartRequest(selectedPresentation, selectedPresentation[0].productId)
        } else {
            null // Handle the case when selectedPresentation is empty
        }
    }

    override fun getItemCount() = showProductCollection.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val selected: CheckBox = itemView.findViewById(R.id.selected_presentation)
        val btnMinus: ImageView = itemView.findViewById(R.id.mins)
        val btnPlus: ImageView = itemView.findViewById(R.id.plus)
        val quantity: TextView = itemView.findViewById(R.id.quantity)

        val weight: TextView = itemView.findViewById(R.id.weight)
        val price: TextView = itemView.findViewById(R.id.price)
    }

    private fun calculateTotalPrice() {
        totalCartPrice = 0.0
        for (item in showProductCollection) {
            if (item.isChecked) {
                totalCartPrice += item.price?.times(item.units) ?: 0.0
            }
        }
        cartUpdated(totalCartPrice.formatted)
    }

    private fun findPresentationInShowOffer(presentationId: String): com.doubleclick.restaurant.feature.shop.showOffer.data.Presentation? {
        showOfferData?.products?.forEach { product ->
            product.presentations.forEach { presentation ->
                if (presentation.id == presentationId) {
                    return presentation
                }
            }
        }

        return null
    }
}
