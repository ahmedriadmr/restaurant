package com.doubleclick.rovleapp.feature.home

import android.os.Bundle
import android.view.View
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
import com.doubleclick.rovleapp.feature.home.data.Categories
import com.doubleclick.rovleapp.feature.home.presentation.CategoryAdapter
import com.doubleclick.rovleapp.feature.home.presentation.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val categoriesListAdapter = CategoryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(viewModel) {
            observe(listCategories) { categories -> renderListCategories(categories){categoriesListAdapter.submitList(null)}}
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getCategories()
        }

        binding.rvCategory.adapter = categoriesListAdapter
    }



    private fun renderListCategories(categories: List<Categories>, refreshData: (() -> Unit)?) {
        when {
            categories.isEmpty() -> refreshData?.invoke()
            else -> categoriesListAdapter.submitList(categories)
        }
    }



    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

}




