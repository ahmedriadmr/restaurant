package com.doubleclick.restaurant.feature.home.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentHomeBinding
import com.doubleclick.restaurant.feature.chef.presentation.ChefActivity
import com.doubleclick.restaurant.feature.home.data.Categories.Categories
import com.doubleclick.restaurant.feature.home.data.Categories.Item
import com.doubleclick.restaurant.feature.home.data.PutCart.request.PutCartRequest
import com.doubleclick.restaurant.feature.home.data.PutCart.response.PutCartResponse
import com.doubleclick.restaurant.feature.home.presentation.adapter.CategoryAdapter
import com.doubleclick.restaurant.feature.home.presentation.adapter.RestaurantDishAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val categoriesListAdapter = CategoryAdapter()
    private val dishesListAdapter = RestaurantDishAdapter()
    private var isCategoryExpanded = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(viewModel) {
            observe(listCategories) { categories -> renderListCategories(categories){categoriesListAdapter.submitList(null)}}
            observe(putCart, ::renderPutCart)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getCategories()
        }

        binding.rvCategory.adapter = categoriesListAdapter
        binding.rvDishes.adapter = dishesListAdapter

        categoriesListAdapter.clickShowCategory = { items ->
            // Render the list of items when a category is clicked
            renderListDishes(items)
        }

        dishesListAdapter.clickShowItem = { id , total ->
            viewModel.putCart(PutCartRequest("1" , id , total))
        }

        binding.allOrders.setOnClickListener {
            startActivity(Intent(requireActivity(), ChefActivity::class.java))
        }

        binding.seeAll.setOnClickListener {
            val layoutManager = binding.rvCategory.layoutManager as LinearLayoutManager
            if (isCategoryExpanded) {
                // Change orientation to vertical and span count to 1
                val linearLayoutManager = LinearLayoutManager(requireContext())
                linearLayoutManager.orientation = RecyclerView.HORIZONTAL
                binding.rvCategory.layoutManager = linearLayoutManager
                binding.rvCategory.invalidate()
                binding.rvCategory.requestLayout()
                isCategoryExpanded = false
            } else {
                // Change orientation to grid and span count to 3
                val gridLayoutManager = GridLayoutManager(requireContext(), 3)
                binding.rvCategory.layoutManager = gridLayoutManager
                binding.rvCategory.invalidate()
                binding.rvCategory.requestLayout()
                isCategoryExpanded = true
            }
        }
    }



    private fun renderListCategories(categories: List<Categories>, refreshData: (() -> Unit)?) {
        when {
            categories.isEmpty() -> refreshData?.invoke()
            else -> categoriesListAdapter.submitList(categories)
        }
    }
    private fun renderPutCart( data: PutCartResponse) {
        Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
    }
    private fun renderListDishes(items: List<Item>) {
        val nonVipItems = items.filter { it.vip == "0" }
        viewLifecycleOwner.lifecycleScope.launch {
            if(viewModel.appSettingsSource.user().firstOrNull()?.role == "user"){
                dishesListAdapter.submitList(nonVipItems)
            } else if (appSettingsSource.user().firstOrNull()?.role == "waiter"){
                dishesListAdapter.submitList(items)
            } else {
                dishesListAdapter.submitList(nonVipItems)
            }
        }

    }



    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

}




