package com.doubleclick.restaurant.feature.shop

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.size.Scale
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observeOrNull
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.FragmentShopBinding
import com.doubleclick.restaurant.feature.auth.AuthActivity
import com.doubleclick.restaurant.feature.profile.ProfileActivity
import com.doubleclick.restaurant.feature.shop.content.AllProductFragment
import com.doubleclick.restaurant.feature.shop.content.SortFragment
import com.doubleclick.restaurant.feature.shop.content.filter.FilterFragment
import com.doubleclick.restaurant.feature.shop.response.ProductData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopFragment : BaseFragment(R.layout.fragment_shop) {

    private val viewModel: ShopViewModel by activityViewModels()
    private val binding by viewBinding(FragmentShopBinding::bind)
    private lateinit var pagerAdapter: ScreenSlidePagerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        listeners()
        with(viewModel) {
            observeOrNull(filter, ::renderFilter)
            observeOrNull(allProductsSorted, ::renderSort)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }
        binding.header.title.text = getString(R.string.store_title)
        binding.header.flCart.visibility = View.VISIBLE


    // Collect the cart size using Flow and update the UI
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            appSettingsSource.cartInventory().collect { cartSize ->
                // Update the text of the TextView
                binding.header.cartSize.text = cartSize.toString()
            }
        }
    }

    }


    private lateinit var productsFragment: AllProductFragment
    private lateinit var filterFragment: FilterFragment
    private lateinit var sortFragment: SortFragment

    private fun initViewPager() {
        productsFragment = AllProductFragment.newInstance()
        filterFragment = FilterFragment.newInstance()
        sortFragment = SortFragment.newInstance()


        val list = listOf(productsFragment, filterFragment,sortFragment)

        pagerAdapter = ScreenSlidePagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle).also { it.setFragmentList(list) }
        binding.viewpager.apply {
            adapter = pagerAdapter
            isUserInputEnabled = false
        }

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectPager(position)
            }
        })
    }

    private fun selectPager(page: Int) {
        when (page) {
            0 -> productsFragment.setupSwipeRefresh(binding.swipeRefresh)
            1 -> filterFragment.setupSwipeRefresh(binding.swipeRefresh)
            2 -> sortFragment.setupSwipeRefresh(binding.swipeRefresh)
        }
    }

    private fun listeners() {
        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.header.flCart.setOnClickListener {
            findNavController().navigate(ShopFragmentDirections.actionShopFragmentToCartListFragment())
        }

        binding.filter.setOnClickListener {
            findNavController().navigate(ShopFragmentDirections.actionShopFragmentToFilterSelectProduct(activity?.intent?.getStringExtra("providerId")))
        }

        binding.sort.setOnClickListener {
            findNavController().navigate(ShopFragmentDirections.actionShopFragmentToSortingInShopFragment())
        }


        activity?.intent?.getStringExtra("providerId")?.let { providerId ->
            viewModel.filterProducts(
                "",
                emptyList(),
                "",
                "",
                listOf(providerId),
                "",
                "",
                "",
                "",
                1,
                true
            )
        }
    }

    private fun renderFilter(filterState: ProductData?) {
        filterState?.let {
            binding.viewpager.setCurrentItem(1, false)
        }
    }

    private fun renderSort(sortState: ProductData?) {
        sortState?.let {
            binding.viewpager.setCurrentItem(2, false)
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

    override fun renderFailure(message: String?) {
        super.renderFailure(message)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun renderFeatureFailure(message: String?) {
        super.renderFeatureFailure(message)
        binding.swipeRefresh.isRefreshing = false
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}