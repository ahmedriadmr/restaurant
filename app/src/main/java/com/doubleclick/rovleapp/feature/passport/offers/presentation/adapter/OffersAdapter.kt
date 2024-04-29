package com.doubleclick.rovleapp.feature.passport.offers.presentation.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.inflate
import com.doubleclick.rovleapp.core.extension.isVisible
import com.doubleclick.rovleapp.databinding.LayoutItemOffersBinding
import com.doubleclick.rovleapp.feature.passport.offers.data.Provider


class OffersAdapter : ListAdapter<Provider, OffersAdapter.ViewHolder>(Differ) {

    internal var clickListenerApp: (String, String , String) -> Unit = { _,_, _ -> }
    internal var clickListenerShop: () -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.layout_item_offers))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutItemOffersBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickListenerApp, clickListenerShop)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val adapter = LevelDetailsAdapter()
        private val adapter2 = LevelDetailsAdapter()
        private val adapter3 = LevelDetailsAdapter()
        fun bind(
            binding: LayoutItemOffersBinding,
            item: Provider,
            clickListenerApp: (String, String , String) -> Unit,
            clickListenerShop: () -> Unit
        ) {
            adapter.clickListenerApp = { selectedString1 , selectedString2 ->
                clickListenerApp(item.id, selectedString1,selectedString2 )
            }
            adapter2.clickListenerApp = { selectedString1 , selectedString2 ->
                clickListenerApp(item.id, selectedString1,selectedString2 )
            }
            adapter3.clickListenerApp = { selectedString1 , selectedString2 ->
                clickListenerApp(item.id, selectedString1,selectedString2 )
            }

            adapter.clickListenerShop = {
                clickListenerShop()
            }
            adapter2.clickListenerShop = {
                clickListenerShop()
            }
            adapter3.clickListenerShop = {
                clickListenerShop()
            }

            binding.provider.text = item.commercial_name
            binding.userPoints.text = item.points
            binding.openLevels.setOnClickListener {
                if (binding.container.isVisible()) {
                    binding.arrow.animate().rotation(-90f).start()
                    binding.container.visibility = View.GONE
                } else {
                    binding.arrow.animate().rotation(0f).start()
                    binding.container.visibility = View.VISIBLE
                }
            }

            binding.level1.text = itemView.context.getString(R.string.level_1)
            if (!item.level1.isNullOrEmpty()) {
                binding.arrowIndicator1.setImageResource(R.drawable.arrow)
                binding.rvLevel1.adapter = adapter
                adapter.submitList(item.level1)
                binding.lllevel1.setOnClickListener {
                    when (binding.rvLevel1.isVisible()) {
                        true -> {
                            binding.rvLevel1.visibility = View.GONE
                            binding.arrowIndicator1.rotation = -90f
                        }

                        false -> {
                            binding.rvLevel1.visibility = View.VISIBLE
                            binding.arrowIndicator1.rotation = 0f
                        }
                    }
                }
            } else {
                setLocker(binding.arrowIndicator1, binding.level1)
            }


            binding.level2.text = itemView.context.getString(R.string.level_2)
            if (!item.level2.isNullOrEmpty()) {
                binding.arrowIndicator2.setImageResource(R.drawable.arrow)
                binding.rvLevel2.adapter = adapter2
                adapter2.submitList(item.level2)
                binding.lllevel2.setOnClickListener {
                    when (binding.rvLevel2.isVisible()) {
                        true -> {
                            binding.rvLevel2.visibility = View.GONE
                            binding.arrowIndicator2.rotation = -90f
                        }

                        false -> {
                            binding.rvLevel2.visibility = View.VISIBLE
                            binding.arrowIndicator2.rotation = 0f
                        }
                    }
                }
            } else {
                setLocker(binding.arrowIndicator2, binding.level2)
            }

            binding.level3.text = itemView.context.getString(R.string.level_3)

            if (!item.level3.isNullOrEmpty()) {
                binding.arrowIndicator3.setImageResource(R.drawable.arrow)
                binding.rvLevel3.adapter = adapter3
                adapter3.submitList(item.level3)
                binding.lllevel3.setOnClickListener {
                    when (binding.rvLevel3.isVisible()) {
                        true -> {
                            binding.rvLevel3.visibility = View.GONE
                            binding.arrowIndicator3.rotation = -90f
                        }

                        false -> {
                            binding.rvLevel3.visibility = View.VISIBLE
                            binding.arrowIndicator3.rotation = 0f
                        }
                    }
                }
            } else {
                setLocker(binding.arrowIndicator3, binding.level3)
            }
        }


        private fun setLocker(arrow: ImageView, header: TextView) {
            arrow.setImageResource(R.drawable.locker)
            arrow.rotation = 0f
            header.setTextColor(ContextCompat.getColor(header.context, com.doubleclick.kalert.R.color.gray_btn_bg_color))
        }
    }

    object Differ : DiffUtil.ItemCallback<Provider>() {
        override fun areItemsTheSame(oldItem: Provider, newItem: Provider): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Provider, newItem: Provider): Boolean {
            return oldItem == newItem
        }
    }
}