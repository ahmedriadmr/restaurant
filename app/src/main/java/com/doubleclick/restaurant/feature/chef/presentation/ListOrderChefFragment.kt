package com.doubleclick.restaurant.feature.chef.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.databinding.FragmentOrderChefBinding
import com.doubleclick.restaurant.feature.chef.domain.model.Data
import com.doubleclick.restaurant.feature.chef.domain.model.Message
import com.doubleclick.restaurant.feature.chef.domain.model.OrderState
import com.doubleclick.restaurant.feature.chef.domain.model.Status
import com.doubleclick.restaurant.feature.chef.presentation.adapter.OrderChefAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "ListOrderChefFragment"

@AndroidEntryPoint
class ListOrderChefFragment : Fragment() {

    private lateinit var binding: FragmentOrderChefBinding
    private val chefViewModel by viewModels<ChefViewModel>()

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
        binding = FragmentOrderChefBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrder()
        binding.swipeRefreshLayout.setOnRefreshListener {
            getOrder()
            binding.swipeRefreshLayout.isRefreshing = true
        }
    }

    private fun getOrder() = lifecycleScope.launch {
        chefViewModel.getOrders(Status(OrderState.ONGOING.value)).collect {
            it.fold(::failure, ::success)
        }
    }

    private fun finish(order_id:String) = lifecycleScope.launch {
        chefViewModel.finishOrder(order_id).collect{
            it.fold(::failure,::finishSuccess)
        }
    }

    private fun finishSuccess(message: Message) {
        Toast.makeText(requireActivity(), message.message,Toast.LENGTH_SHORT).show()
        getOrder()
    }

    private fun success(data: List<Data>) {
        Log.d(TAG, "good: $data")
        binding.rvOrders.adapter = OrderChefAdapter(data){
            finish(it)
        }
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun failure(failure: Failure) {
        Log.d(TAG, "failure: $failure")
        binding.swipeRefreshLayout.isRefreshing = false
    }


}