package com.doubleclick.restaurant.feature.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentHomeBinding
import com.doubleclick.restaurant.feature.home.data.Categories.Categories
import com.doubleclick.restaurant.feature.home.data.Categories.Item
import com.doubleclick.restaurant.feature.home.data.PutCart.request.PutCartRequest
import com.doubleclick.restaurant.feature.home.data.PutCart.response.PutCartResponse
import com.doubleclick.restaurant.feature.home.presentation.adapter.CategoryAdapter
import com.doubleclick.restaurant.feature.home.presentation.adapter.RestaurantDishAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val categoriesListAdapter = CategoryAdapter()
    private val dishesListAdapter = RestaurantDishAdapter()

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

        dishesListAdapter.clickShowItem = { id ->
            viewModel.putCart(PutCartRequest("1" , id))
        }
    }



    private fun renderListCategories(categories: List<Categories>, refreshData: (() -> Unit)?) {
        when {
            categories.isEmpty() -> refreshData?.invoke()
            else -> categoriesListAdapter.submitList(categories)
        }
    }
    private fun renderPutCart(@Suppress("UNUSED_PARAMETER") data: PutCartResponse) {
        Toast.makeText(requireContext(), "Cart Stored Successfully", Toast.LENGTH_SHORT).show()
    }
    private fun renderListDishes(items: List<Item>) {
        val nonVipItems = items.filter { it.vip == "0" }
        dishesListAdapter.submitList(nonVipItems)
    }



    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

}




