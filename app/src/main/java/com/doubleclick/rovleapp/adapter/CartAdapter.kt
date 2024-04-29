package com.doubleclick.restaurant.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.domain.model.carts.get.CartsModel
import com.doubleclick.domain.ts.OnUpdateCart
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.utils.Constant.BASE_URL_IMAGE
import com.doubleclick.swipetoactionlayout.ActionBindHelper
import com.doubleclick.swipetoactionlayout.SwipeAction
import com.doubleclick.swipetoactionlayout.SwipeMenuListener
import com.doubleclick.swipetoactionlayout.SwipeToActionLayout


//lambda function
typealias OnActionClicked = (contact: CartsModel, action: SwipeAction) -> Unit
typealias Update = (input: Int,cartsModel:CartsModel) -> Unit

class CartAdapter(
    val items: List<CartsModel>,
    private val onUpdateCart: OnUpdateCart,
    private val actionClicked: OnActionClicked,
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val actionsBindHelper = ActionBindHelper()
    private val TAG = "CartAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_swipe_to_action, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        var quantity = items[holder.bindingAdapterPosition].number
        actionsBindHelper.bind("", holder.swipeToActionLayout)
        holder.bind()
        Log.e(TAG, "onBindViewHolder: ${items[holder.bindingAdapterPosition].size.name}")
        Glide.with(holder.itemView.context)
            .load(BASE_URL_IMAGE + items[holder.bindingAdapterPosition].size.item.image)
            .into(holder.image)
        holder.price.text = buildString {
            append(items[holder.bindingAdapterPosition].size.price)
        }
        holder.name.text = buildString {
            append(items[holder.bindingAdapterPosition].size.item.name)
        }
        holder.size.text = buildString {
            append(items[holder.bindingAdapterPosition].size.name)
        }
        holder.quantity.text = quantity.toString()
        holder.plus.setOnClickListener {
            quantity++
            onUpdateCart.updateItem(quantity,items[holder.bindingAdapterPosition])
            holder.quantity.text = buildString {
                append(quantity)
            }
        }
        holder.mins.setOnClickListener {
            if (quantity > 0) {
                quantity--
                onUpdateCart.updateItem(quantity,items[holder.bindingAdapterPosition])
                holder.quantity.text = buildString {
                    append(quantity)
                }
            } else {
                quantity = 1
                onUpdateCart.updateItem(quantity,items[holder.bindingAdapterPosition])
                holder.quantity.text = buildString {
                    append(quantity)
                }
                Toast.makeText(
                    holder.itemView.context,
                    "You can't put less than 1",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        SwipeMenuListener {
        val swipeToActionLayout: SwipeToActionLayout =
            itemView.findViewById(R.id.swipe_to_action_layout)
        val name: TextView = itemView.findViewById(R.id.name)
        val size: TextView = itemView.findViewById(R.id.size)
        val price: TextView = itemView.findViewById(R.id.price)
        val image: ImageView = itemView.findViewById(R.id.image)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
        val plus: ImageView = itemView.findViewById(R.id.plus)
        val mins: ImageView = itemView.findViewById(R.id.mins)

        fun bind() {
            swipeToActionLayout.menuListener = this
        }

        override fun onClosed(view: View) {
            try {
                val cart = items[bindingAdapterPosition]
                actionsBindHelper.closeOtherThan(cart.id.toString())
            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
            }
        }

        override fun onOpened(view: View) {

        }

        override fun onFullyOpened(view: View, quickAction: SwipeAction) {

        }

        override fun onActionClicked(view: View, action: SwipeAction) {
            try {
                actionClicked(items[bindingAdapterPosition], action)
                swipeToActionLayout.close()
            } catch (e: IndexOutOfBoundsException) {
                Log.e(TAG, "onActionClicked: ${e.message}")
            }
        }
    }


}