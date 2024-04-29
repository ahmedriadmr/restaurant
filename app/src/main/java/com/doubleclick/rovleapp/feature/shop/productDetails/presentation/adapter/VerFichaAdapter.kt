package com.doubleclick.rovleapp.feature.shop.productDetails.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.databinding.LayoutItemVerFichaBinding
import com.doubleclick.rovleapp.feature.shop.response.Presentation
import com.doubleclick.rovleapp.feature.shop.showOffer.data.ShowOfferData
import com.doubleclick.rovleapp.utils.Constant.euroSign

class VerFichaAdapter : ListAdapter<Presentation, VerFichaAdapter.ViewHolder>(Differ) {

    private var showOfferData: ShowOfferData? = null

    fun implementShowOfferData(data: ShowOfferData?) {
        showOfferData = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.layout_item_ver_ficha))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemVerFichaBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position))

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemVerFichaBinding, item: Presentation
        ) {
            findPresentationInShowOffer(item.id)?.let { offer -> item.price = offer.after_dicount_price
            }
            binding.weight.text = itemView.context.getString(R.string.weight_description, item.weight)
            binding.price.text = itemView.context.getString(R.string.price_description, item.price.toString(), euroSign)
        }
    }
    private fun findPresentationInShowOffer(presentationId: String): com.doubleclick.rovleapp.feature.shop.showOffer.data.Presentation? {
        showOfferData?.products?.forEach { product ->
            product.presentations.forEach { presentation ->
                if (presentation.id == presentationId) {
                    return presentation
                }
            }
        }

        return null
    }

    object Differ : DiffUtil.ItemCallback<Presentation>() {
        override fun areItemsTheSame(oldItem: Presentation, newItem: Presentation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Presentation, newItem: Presentation): Boolean {
            return oldItem == newItem
        }
    }
}