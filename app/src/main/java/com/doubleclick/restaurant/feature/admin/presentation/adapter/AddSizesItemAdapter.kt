package com.doubleclick.restaurant.feature.admin.presentation.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.databinding.LayoutItemSizeBinding
import com.doubleclick.restaurant.feature.admin.data.addProduct.request.Size


class AddSizesItemAdapter: RecyclerView.Adapter<AddSizesItemAdapter.ViewHolder>() {
    private val items = mutableListOf(Size())
    internal var clickListener: (List<Size>) -> Unit = { _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutItemSizeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position],clickListener)
    }

    override fun getItemCount(): Int = items.size

    fun addItem(size: Size) {
        items.add(size)
        notifyItemInserted(items.size - 1)
        clickListener(items) // Update the list whenever an item is added
    }

    fun removeItem(position: Int) {
        if (position in items.indices) {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
            clickListener(items) // Update the list whenever an item is removed
        }
    }
    inner class ViewHolder(private val binding: LayoutItemSizeBinding,) : RecyclerView.ViewHolder(binding.root) {
        fun bind(size: Size,clickListener: (List<Size>) -> Unit = { _ -> }) {
            // Bind data to views
            binding.sizeName.setText(size.name)
            binding.sizePrice.setText(size.price)

            binding.sizeName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    size.name = s.toString()
                    clickListener(items) // Update the list whenever the name changes
                }
            })

            binding.sizePrice.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    size.price = s.toString()
                    clickListener(items) // Update the list whenever the price changes
                }
            })
            binding.delete.setOnClickListener {
                removeItem(position)
            }



        }
    }
}