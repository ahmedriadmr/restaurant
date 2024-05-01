package com.doubleclick.restaurant.activities.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.customspinner.OnSpinnerEventsListener
import com.doubleclick.domain.model.carts.get.CartsModel
import com.doubleclick.domain.ts.OnClickAlert
import com.doubleclick.restaurant.utils.collapse
import com.doubleclick.restaurant.utils.expand
import com.doubleclick.restaurant.databinding.FragmentCheckoutBinding
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "CheckoutFragment"

@AndroidEntryPoint
class CheckoutFragment : Fragment(), OnClickAlert {

    private lateinit var binding: FragmentCheckoutBinding
//    private val mainViewModel by viewModels<MainViewModel>()
    private var carts: MutableList<CartsModel> = mutableListOf()

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
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
//        tables()
        setUpSpinner()
//        carts()
    }

//    private fun tables() = lifecycleScope.launch {
//        mainViewModel.tables().collect { response ->
//            if (response.isSuccessful) {
//                response.body()?.data?.let { table ->
//                    binding.tableNumber.adapter = SpinnerAdapterTable(table)
//                }
//            }
//        }
//    }

//    private fun carts() = lifecycleScope.launch {
//        mainViewModel.getCarts().collect { response ->
//            if (response.isSuccessful) {
//                response.body()?.data?.let {
//                    var total = 0.0
//                    carts = it.toMutableList()
//                    binding.rvItems.adapter = OrderItemsAdapter(it)
//                    it.forEach { cart ->
//                        total += cart.number * cart.size.price
//                        binding.totalNumber.text = buildString {
//                            append("$")
//                            append(" ")
//                            append(total)
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun onClick() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.restaurant.setOnClickListener {
            expand(binding.llTableNumber)
            collapse(binding.llLocation)
        }
        binding.delivery.setOnClickListener {
            collapse(binding.llTableNumber)
            expand(binding.llLocation)
        }

        binding.comfirm.setOnClickListener {
            order()
        }
    }

    private fun order() = lifecycleScope.launch {
        val jsonObject = JsonObject()
        val jsonArray = JsonArray()
        var total = 0.0
        carts.forEach {
            total += it.number * it.size.price
            val jsonObject = JsonObject()
            jsonObject.addProperty("item_id", it.size.item_id)
            jsonObject.addProperty("number", it.number)
            jsonObject.addProperty("size_name", it.size.name)
            jsonObject.addProperty("size_price", it.size.price)
            jsonObject.addProperty("total", (it.number * it.size.price))
            jsonArray.add(jsonObject)
        }
        jsonObject.addProperty("total", total)
        jsonObject.addProperty("order_type", "Take_away")
        jsonObject.addProperty("location", "Eslam Ghazy")
        jsonObject.addProperty("table_id", 1)
        jsonObject.addProperty("waiter_id", 1)
        jsonObject.add("items", jsonArray)

//        mainViewModel.setOrders(jsonObject).collect { response ->
//            if (response.isSuccessful) {
//                binding.root.showSnack("Done")
//                AlertDoneDialog(requireActivity(), this@CheckoutFragment).show()
//            }
//        }
        Log.e(TAG, "order: ${jsonObject}")

    }

    override fun dismiss() {
        Toast.makeText(requireActivity(), "Dis", Toast.LENGTH_SHORT).show()
    }

    override fun onClickOk(cartModel: CartsModel?): Job = lifecycleScope.launch {

    }

    private fun setUpSpinner() {
        binding.tableNumber.setSpinnerEventsListener(object : OnSpinnerEventsListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowOpened(spinner: Spinner?) {
                binding.arrow.animate().rotation(180f).start()
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPopupWindowClosed(spinner: Spinner?) {
                binding.arrow.animate().rotation(0f).start()
            }
        })
    }


}