package com.doubleclick.restaurant.presentation.ui.admin.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
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
import com.doubleclick.restaurant.feature.admin.data.addProduct.request.Ingredient
import com.doubleclick.restaurant.feature.admin.data.addProduct.request.Size
import com.doubleclick.restaurant.feature.admin.data.addProduct.response.AddProductResponse
import com.doubleclick.restaurant.feature.admin.presentation.adapter.AddIngredientsAdapter
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
    private var categoryId: String = ""
    private var isVip: Int = 0
    private var isCategorySelected = false
    private var addedSizes = false
    private var addedIngredients = false
    private val addIngredientsAdapter = AddIngredientsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSizes.adapter = adapter
        binding.rvIngredients.adapter = addIngredientsAdapter
        binding.categories.adapter = categoriesListAdapter

        with(viewModel) {
            observe(listCategories) { categories -> renderListCategories(categories) { categoriesListAdapter.submitList(null) } }
            observe(addProduct, ::renderAddProduct)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getCategories()
        }

        binding.llCategory.setOnClickListener {
            binding.categories.visibility = if (binding.categories.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        binding.addMoreSizes.setOnClickListener {
            adapter.addItem(Size("", ""))
        }
        binding.addMoreIngredients.setOnClickListener {
            addIngredientsAdapter.addItem(Ingredient(""))
        }

        categoriesListAdapter.clickListenerChooseCategory = { id ->
            categoryId = id
            isCategorySelected = true
            checkEnableShopInformationButton()
        }

        adapter.clickListener = { sizes, allFilled ->
            addedSizes = allFilled
            checkEnableShopInformationButton()
        }
        addIngredientsAdapter.clickListener = { ingredients, allFilled ->
            addedIngredients = allFilled
            checkEnableShopInformationButton()
        }

        binding.isVip.setOnCheckedChangeListener { _, isChecked ->
            isVip = if (isChecked) 1 else 0
        }

        binding.upload.setOnClickListener {
            viewModel.addProduct(
                AddProductRequest(
                    categoryId.toInt(),
                    binding.description.text.toString(),
                    addIngredientsAdapter.items,
                    binding.name.text.toString(),
                    adapter.items,
                    isVip
                )
            )
        }

        binding.name.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }

        binding.description.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
    }

    private fun renderListCategories(categories: List<Categories>, refreshData: (() -> Unit)?) {
        if (categories.isEmpty()) refreshData?.invoke() else categoriesListAdapter.submitList(categories)
    }

    private fun renderAddProduct(data: AddProductResponse) {
        Toast.makeText(requireContext(), "Product Added Successfully", Toast.LENGTH_SHORT).show()
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

    private fun showInformationButton(isEnabled: Boolean) {
        binding.upload.isEnabled = isEnabled
    }

    private fun checkEnableShopInformationButton() {
        if (view != null) {
            val firstNameEmpty = binding.name.text.isNullOrBlank()
            val descriptionEmpty = binding.description.text.isNullOrBlank()
            showInformationButton(!firstNameEmpty && !descriptionEmpty && isCategorySelected && addedSizes && addedIngredients)
        }
    }
}




