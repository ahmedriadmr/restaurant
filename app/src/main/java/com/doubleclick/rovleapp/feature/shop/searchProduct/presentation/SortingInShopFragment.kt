package com.doubleclick.restaurant.feature.shop.searchProduct.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import coil.size.Scale
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observeOrNull
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseDialogFragment
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.FragmentSortInShopBinding
import com.doubleclick.restaurant.feature.auth.AuthActivity
import com.doubleclick.restaurant.feature.profile.ProfileActivity
import com.doubleclick.restaurant.feature.shop.ShopViewModel
import com.doubleclick.restaurant.feature.shop.response.ProductData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SortingInShopFragment : BaseDialogFragment(R.layout.fragment_sort_in_shop) {
    var initialState = true

    private val binding by viewBinding(FragmentSortInShopBinding::bind)
    private lateinit var navController: NavController
    private val viewModel: ShopViewModel by activityViewModels()
    private var orderBy: String = ""
    private var orderType: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.header.title.text = getString(R.string.store_title)
        with(viewModel) {
            observeOrNull(allProductsSorted, ::renderState)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)

        }

        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.for_you -> {
                    orderBy = "for_you"
                    orderType = "asc"

                }

                R.id.proximity -> {
                    orderBy = "proximity"
                    orderType = "asc"

                }

                R.id.sca_punctuation -> {
                    orderBy = "sca_score"
                    orderType = ""


                }
                R.id.rating -> {
                    orderBy = "rating"
                    orderType = ""


                }
                R.id.price_asc -> {
                    orderBy = "price"
                    orderType = "asc"


                }
                R.id.price_des -> {
                    orderBy = "price"
                    orderType = "desc"
                }
                else -> {
                    orderBy = ""
                    orderType = ""
                }
            }
        }
        binding.confirmSort.setOnClickListener {
            viewModel.getAllProductsSorted(
                1,
                true,
                orderBy,
                orderType
            )
        }
    }


    override fun renderAuthenticating(user: UserAccess?) {
        super.renderAuthenticating(user)

        binding.header.photo.load(user?.image) {
            scale(Scale.FILL)
            placeholder(R.drawable.image)
            error(R.drawable.image)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            val userToken = viewModel.appSettingsSource.user().firstOrNull()?.token

            if (userToken != null && userToken != "-1") {

                binding.header.photo.setOnClickListener { startActivity(Intent(requireActivity(), ProfileActivity::class.java)) }
            } else {

                binding.header.photo.setOnClickListener { startActivity(AuthActivity.callingIntent(requireActivity())) }
            }
        }
    }

    private fun renderState(event: ProductData?) {
        if (!initialState) requireActivity().onBackPressedDispatcher.onBackPressed()
        initialState = false
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}