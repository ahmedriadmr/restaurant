package com.doubleclick.restaurant.feature.subscriptions.providerList.presentation.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.core.extension.moveItemToFirst
import com.doubleclick.restaurant.feature.subscriptions.providerList.data.showPlan.Size
import com.doubleclick.restaurant.utils.Constant.euroSign
import com.doubleclick.restaurant.utils.collapse
import com.doubleclick.restaurant.utils.expand
import kotlin.properties.Delegates

class PlansAdapter : RecyclerView.Adapter<PlansAdapter.ViewHolder>() {

    internal var showPlanCollection: List<Size> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    var planName: String = ""
    var planDescription: String = ""
    private var showPlanCollectionAfterModification: List<Size> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }
    internal var clickSize: (String, List<Size>) -> Unit = { _, _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.layout_item_our_plans))

    override fun getItemCount() = showPlanCollectionAfterModification.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = showPlanCollection[position]

        holder.planName.text = planName
        holder.description.text = planDescription
        holder.weight.text = holder.itemView.context.getString(R.string.weight_placeholder,item.size.weight)
        holder.price.text = holder.itemView.context.getString(R.string.total_cost_placeholder, item.price, euroSign)
        holder.btnSubscription.setOnClickListener {
            clickSize(item.plan_id, showPlanCollection.toMutableList().moveItemToFirst(item))
        }

        holder.llPlan.setOnClickListener {
            if (holder.layoutDescriptionSize.visibility == View.GONE) {
                holder.arrow.animate().rotation(0f).start()
                expand(holder.layoutDescriptionSize)
            } else {
                holder.arrow.animate().rotation(-180f).start()
                collapse(holder.layoutDescriptionSize)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val planName: TextView = itemView.findViewById(R.id.planName)
        val description: TextView = itemView.findViewById(R.id.description)
        val weight: TextView = itemView.findViewById(R.id.weight)
        val price: TextView = itemView.findViewById(R.id.price)
        val btnSubscription: TextView = itemView.findViewById(R.id.btn_subscription)
        val llPlan: LinearLayout = itemView.findViewById(R.id.llplan)
        val layoutDescriptionSize: LinearLayout = itemView.findViewById(R.id.layout_description_size)
        val arrow: ImageView = itemView.findViewById(R.id.arrow)
    }
    fun showLeastSize() {
        // Assuming Size has a Comparable implementation for weight
        val leastSize = showPlanCollection.minByOrNull { it.size.weight }
        showPlanCollectionAfterModification = leastSize?.let { listOf(it) } ?: emptyList()
        notifyDataSetChanged()
    }
}