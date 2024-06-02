package com.doubleclick.restaurant.feature.chef.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.databinding.FragmentOrderChefBinding
import com.doubleclick.restaurant.feature.chef.domain.model.Data
import com.doubleclick.restaurant.feature.chef.domain.model.OrderState
import com.doubleclick.restaurant.feature.chef.domain.model.Status
import com.doubleclick.restaurant.feature.chef.presentation.adapter.HistoryOderChefAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "HistoryOrderChefFragmen"

@AndroidEntryPoint
class HistoryOrderChefFragment : Fragment() {

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
        load()
        binding.swipeRefreshLayout.setOnRefreshListener {
            load()
            binding.swipeRefreshLayout.isRefreshing = true
        }
    }


    private fun load() = lifecycleScope.launch {
        chefViewModel.getOrders(Status(OrderState.DONE.value)).collect {
            it.fold(::failure, ::success)
        }
    }

    private fun success(data: List<Data>) {
        Log.d(TAG, "good: $data")
        binding.rvOrders.adapter = HistoryOderChefAdapter(data)
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun failure(failure: Failure) {
        Log.d(TAG, "failure: $failure")
        binding.swipeRefreshLayout.isRefreshing = false
    }

}