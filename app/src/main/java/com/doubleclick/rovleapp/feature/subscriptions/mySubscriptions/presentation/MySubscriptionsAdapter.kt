package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.presentation

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.LayoutItemMySubscriptionsBinding
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.listSubscriptions.MySubscriptionsData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MySubscriptionsAdapter : ListAdapter<MySubscriptionsData, MySubscriptionsAdapter.ViewHolder>(Differ) {

    internal var clickShowSubscription: (String) -> Unit = { _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_item_my_subscriptions))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemMySubscriptionsBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickShowSubscription)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            binding: LayoutItemMySubscriptionsBinding,
            item: MySubscriptionsData,
            clickShowSubscription: (String) -> Unit
        ) {

            binding.planName.text = item.plan.name
            binding.roasterName.text = item.plan.provider.commercial_name
            fun convertDateFormat(inputDate: String): String {
                val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val outputFormat = SimpleDateFormat("EEE MMM dd yyyy", Locale.getDefault())

                val date: Date = inputFormat.parse(inputDate) ?: Date()
                return outputFormat.format(date)
            }

            val inputDate = item.created_at
            val outputDate = convertDateFormat(inputDate)
            binding.dateArrive.text = outputDate

            if (item.status == "active") {
                binding.Icon1.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,R.drawable.ic_motorcycle))
                binding.subscriptionNotPayedText.visibility = View.GONE
            } else {
                binding.Icon1.visibility = View.GONE
                binding.subscriptionNotPayedText.visibility = View.VISIBLE
            }

            binding.show.setOnClickListener {
                clickShowSubscription(item.id)
            }

        }

    }

    object Differ : DiffUtil.ItemCallback<MySubscriptionsData>() {
        override fun areItemsTheSame(
            oldItem: MySubscriptionsData,
            newItem: MySubscriptionsData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MySubscriptionsData,
            newItem: MySubscriptionsData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
