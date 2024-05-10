package com.doubleclick.restaurant.feature.home.presentation.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.formatted
import com.doubleclick.restaurant.core.extension.inflate
import com.doubleclick.restaurant.databinding.ItemSwipeToActionBinding
import com.doubleclick.restaurant.feature.home.data.listCart.CartData
import com.doubleclick.restaurant.utils.Constant
import com.doubleclick.restaurant.utils.Constant.dollarSign
import com.doubleclick.swipetoactionlayout.ActionBindHelper
import com.doubleclick.swipetoactionlayout.SwipeAction
import com.doubleclick.swipetoactionlayout.SwipeMenuListener
import com.doubleclick.swipetoactionlayout.SwipeToActionLayout

typealias OnActionClicked = (contact: CartData, action: SwipeAction) -> Unit

private const val TAG = "CartAdapter"

class CartAdapter : ListAdapter<CartData, CartAdapter.ViewHolder>(Differ) {

    internal var clickUpdateCart: (String, String, String) -> Unit = { _, _, _ -> }
    internal var cartUpdated: (String) -> Unit = { _ -> }
    internal var deleteCart: (String) -> Unit = { _ -> }
    private var totalCartPrice: Double = 0.0
    private lateinit var actionClicked: OnActionClicked
    private val actionsBindHelper = ActionBindHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_swipe_to_action))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = ItemSwipeToActionBinding.bind(holder.itemView)
        holder.bind(binding, getItem(position), clickUpdateCart,deleteCart)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), SwipeMenuListener {
        private lateinit var swipeToActionLayout: SwipeToActionLayout
        fun bind(
            binding: ItemSwipeToActionBinding,
            cart: CartData,
            clickUpdateCart: (String, String, String) -> Unit,
            deleteCart: (String) -> Unit = { _ -> }
        ) {
            swipeToActionLayout = binding.swipeToActionLayout
            binding.swipeToActionLayout.menuListener = this
            binding.itemCart.name.text = cart.size.item.name
            binding.itemCart.size.text = cart.size.name
            binding.itemCart.price.text = "$dollarSign${cart.size.price}"
            binding.itemCart.quantity.text = cart.number.toString()
            deleteCart(cart.id)
            calculateTotalPrice(currentList)
            if (!cart.size.item.image.isNullOrEmpty()) {
                binding.itemCart.image.load(Constant.BASE_URL_IMAGE_ITEMS + cart.size.item.image) {
                    crossfade(true)
                    placeholder(R.drawable.image)
                    error(R.drawable.image)
                }
            }
            binding.itemCart.plus.setOnClickListener {
                val currentQuantity = binding.itemCart.quantity.text.toString().toInt()
                val newQuantity = currentQuantity + 1
                binding.itemCart.quantity.text = newQuantity.toString()
                clickUpdateCart(cart.id, newQuantity.toString(), cart.size_id)
                calculateTotalPrice(currentList)
            }

            binding.itemCart.minus.setOnClickListener {
                val currentQuantity = binding.itemCart.quantity.text.toString().toInt()
                if (currentQuantity > 1) {
                    val newQuantity = currentQuantity - 1
                    binding.itemCart.quantity.text = newQuantity.toString()
                    clickUpdateCart(cart.id, newQuantity.toString(), cart.size_id)
                }
                calculateTotalPrice(currentList)
            }


        }

        override fun onClosed(view: View) {
            try {
                val cart = getItem(this.bindingAdapterPosition)
                actionsBindHelper.closeOtherThan(cart.id.toString())
            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
            }
        }

        override fun onOpened(view: View) {
            //nothing
        }

        override fun onFullyOpened(view: View, quickAction: SwipeAction) {
            //nothing
        }

        override fun onActionClicked(view: View, action: SwipeAction) {
            try {
                actionClicked(getItem(this.bindingAdapterPosition), action)
                swipeToActionLayout.close()
            } catch (e: IndexOutOfBoundsException) {
                Log.e(TAG, "onActionClicked: ${e.message}")
            }
        }

    }

    private fun calculateTotalPrice(cartItems: List<CartData>) {
        totalCartPrice = 0.0
        for (item in cartItems) {
            totalCartPrice += item.size.price * item.number
        }
        cartUpdated(totalCartPrice.formatted)
    }

    object Differ : DiffUtil.ItemCallback<CartData>() {
        override fun areItemsTheSame(oldItem: CartData, newItem: CartData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartData, newItem: CartData): Boolean {
            return oldItem == newItem
        }
    }

    fun onActionClicked(actionClicked: OnActionClicked) {
        this.actionClicked = actionClicked
    }
}