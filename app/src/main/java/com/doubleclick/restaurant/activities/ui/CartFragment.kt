package com.doubleclick.restaurant.activities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.domain.model.carts.get.CartsModel
import com.doubleclick.domain.ts.OnUpdateCart
import com.doubleclick.restaurant.adapter.CartAdapter
import com.doubleclick.restaurant.databinding.FragmentCartNewBinding
import com.doubleclick.swipetoactionlayout.SwipeAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


private const val TAG = "CartFragment"

@AndroidEntryPoint
class CartFragment : Fragment(), OnUpdateCart {

    private lateinit var binding: FragmentCartNewBinding

    //    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var cartAdapter: CartAdapter
    private var cartsModelList: MutableList<CartsModel> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
//        carts()
    }

//    private fun carts() = lifecycleScope.launch {
//        mainViewModel.getCarts().collect { response ->
//            if (response.isSuccessful) {
//                response.body()?.data?.let {
//                    cartsModelList = it.toMutableList()
//                    calculateTotal(cartsModelList)
//                    cartAdapter = CartAdapter(
//                        it,
//                        onUpdateCart = this@CartFragment,
//                        actionClicked = ::onActionClicked
//                    )
//                    binding.rvCart.adapter = cartAdapter
//                }
//            }
//        }
//    }

    private fun calculateTotal(cartsModelList: List<CartsModel>) {
        var total = 0.0
        cartsModelList.forEach { cart ->
            total += cart.number * cart.size.price
            binding.totalNumber.text = buildString {
                append("$")
                append(" ")
                append(total)
            }
        }
    }


    private fun onClick() {
        binding.checkout.setOnClickListener {
            updateCart()
        }
    }

    private fun onActionClicked(cartModel: CartsModel, action: SwipeAction) {
//        when (action.actionId) {
//            R.id.delete -> {
//                AlertDeleteDialog(requireActivity(), cartModel, this@CartFragment).show()
//            }
//        }
    }

    fun dismiss() {
        //nothing...
    }




//    override fun onClickOk(cartModel: CartsModel?): Job = lifecycleScope.launch {
//        mainViewModel.deleteCart(cartModel?.id ?: -1).collect { response ->
//            if (response.isSuccessful) {
//                Toast.makeText(requireActivity(), "${response.body()?.message}", Toast.LENGTH_SHORT)
//                    .show()
//                carts()
//            }
//        }
//    }

    override fun updateItem(input: Int, cartsModel: CartsModel) {
        cartsModel.number = input
        cartsModelList[cartsModelList.indexOf(cartsModel)] = cartsModel
        calculateTotal(cartsModelList)
    }

    private fun updateCart() = lifecycleScope.launch {
        binding.loading.root.isVisible = true
//        cartsModelList.forEach { cartsModel ->
//            mainViewModel.updateCart(number = cartsModel.number, cartsModel.id)
//                .collect { response ->
//                    if (response.isSuccessful) {
//                        Log.e(TAG, "count: ${response.body()?.message}")
//                        binding.loading.root.isVisible = false
//                    }
//                }
//        }
        findNavController().navigate(CartFragmentDirections.actionCartFragmentToCheckoutFragment())
    }

}