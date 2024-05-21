package com.doubleclick.restaurant.presentation.ui.admin.ui

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
import com.doubleclick.restaurant.databinding.FragmentAddProductBinding
import com.doubleclick.restaurant.feature.admin.AdminViewModel
import com.doubleclick.restaurant.feature.admin.data.addProduct.request.AddProductRequest
import com.doubleclick.restaurant.feature.admin.data.addProduct.request.Size
import com.doubleclick.restaurant.feature.admin.data.addProduct.response.AddProductResponse
import com.doubleclick.restaurant.feature.admin.presentation.adapter.AddSizesItemAdapter
import com.doubleclick.restaurant.feature.admin.presentation.adapter.SelectCategoryItemAdapter
import com.doubleclick.restaurant.feature.home.data.Categories.Categories
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : BaseFragment(R.layout.fragment_add_product) {
    private val binding by viewBinding(FragmentAddProductBinding::bind)
    private val viewModel: AdminViewModel by viewModels()
    private val adapter = AddSizesItemAdapter()
    private val categoriesListAdapter = SelectCategoryItemAdapter()
    private var categoryId : String = ""
    private var allSizes : List<Size> = emptyList()
    private var isVip : Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSizes.adapter = adapter
        binding.categories.adapter = categoriesListAdapter

        with(viewModel) {
            observe(listCategories) { categories -> renderListCategories(categories){categoriesListAdapter.submitList(null)}}
            observe(addProduct, ::renderAddProduct)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getCategories()
        }

        binding.addMore.setOnClickListener {
            // For example, add a new size with default values or from input fields
            adapter.addItem(Size("", ""))
        }
        categoriesListAdapter.clickListenerChooseCategory = { id ->
            categoryId = id
        }

        adapter.clickListener = { sizes ->
            allSizes = sizes
        }
        binding.isVip.setOnCheckedChangeListener { _, isChecked ->
            isVip = if (isChecked) 1 else 0
        }
        binding.upload.setOnClickListener {
            viewModel.addProduct(AddProductRequest(categoryId.toInt(),binding.description.text.toString(),binding.name.text.toString(),allSizes,isVip))
        }

    }
    private fun renderListCategories(categories: List<Categories>, refreshData: (() -> Unit)?) {
        when {
            categories.isEmpty() -> refreshData?.invoke()
            else -> categoriesListAdapter.submitList(categories)
        }
    }

    private fun renderAddProduct(data: AddProductResponse) {
        Toast.makeText(requireContext(), "Product Added Successfully", Toast.LENGTH_SHORT).show()
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }


}




