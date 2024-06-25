package com.doubleclick.restaurant.feature.admin.presentation.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.databinding.LayoutAddIngredientBinding
import com.doubleclick.restaurant.feature.admin.data.addProduct.request.Ingredient

class AddIngredientsAdapter : RecyclerView.Adapter<AddIngredientsAdapter.ViewHolder>() {
    val items = mutableListOf(Ingredient())
    internal var clickListener: (List<Ingredient>, Boolean) -> Unit = { _,_ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutAddIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    override fun getItemCount(): Int = items.size

    fun addItem(ingredient: Ingredient) {
        items.add(ingredient)
        notifyItemInserted(items.size - 1)
        checkIfAllSizesFilled()
    }

    fun removeItem(position: Int) {
        if (position in items.indices) {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
            checkIfAllSizesFilled()
        }
    }

    private fun checkIfAllSizesFilled() {
        val allFilled = items.all { it.name.isNotEmpty()  }
        clickListener(items, allFilled)
    }

    inner class ViewHolder(private val binding: LayoutAddIngredientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Ingredient, clickListener: (List<Ingredient>, Boolean) -> Unit) {
            binding.ingredientName.setText(ingredient.name)

            binding.ingredientName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    ingredient.name = s.toString()
                    checkIfAllSizesFilled()
                }
            })



            binding.deleteIngredient.setOnClickListener {
                removeItem(adapterPosition)
            }



        }
    }
}