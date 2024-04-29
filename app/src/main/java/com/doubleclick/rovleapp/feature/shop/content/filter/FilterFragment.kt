package com.doubleclick.rovleapp.feature.shop.content.filter

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.invisible
import com.doubleclick.rovleapp.core.extension.observeOrNull
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.extension.visible
import com.doubleclick.rovleapp.core.platform.BaseFragment
import com.doubleclick.rovleapp.databinding.FragmentContentBinding
import com.doubleclick.rovleapp.feature.shop.ShopAdapter
import com.doubleclick.rovleapp.feature.shop.ShopFragmentDirections
import com.doubleclick.rovleapp.feature.shop.ShopViewModel
import com.doubleclick.rovleapp.feature.shop.response.ProductData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : BaseFragment(R.layout.fragment_content) {

    private val binding by viewBinding(FragmentContentBinding::bind)
    private val viewModel: ShopViewModel by activityViewModels()

    private lateinit var swipeRefresh: SwipeRefreshLayout

    var page: Int? = 1
    var isLoading = false
    var isScrolling = false
    var queryPageSize = 1
    var isLastPage = false
    private lateinit var adapter: ShopAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeOrNull(viewModel.filter, ::renderState)
    }

    fun setupSwipeRefresh(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefresh = swipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            page = 1
            isLoading = false
            isScrolling = false
            queryPageSize = 1
            isLastPage = false
            adapter.submitList(null)
            page?.let {
                viewModel.filterProducts(page = it, isRefresh = true)
            }
        }
    }

    private fun init() {
        adapter = ShopAdapter(requireActivity())
        val offerId = arguments?.getString("offerId")
        binding.rv.adapter = adapter
        binding.rv.addOnScrollListener(scrollListener)

        adapter.clickListenerCart = { product ->
            findNavController().navigate(
                ShopFragmentDirections.actionShopFragmentToProductDetailsFragment(
                    product,  offerId
                )
            )
        }

        adapter.clickListenerDetails = { product ->
            findNavController().navigate(
                ShopFragmentDirections.actionShopFragmentToVerFichaFragment(
                    product, offerId
                )
            )
        }

        adapter.clickListener = { product ->
            findNavController().navigate(
                ShopFragmentDirections.actionShopFragmentToVerFichaFragment(
                    product, offerId
                )
            )
        }
    }



    private fun renderState(products: ProductData?) {
        products?.let {
            if (::swipeRefresh.isInitialized) swipeRefresh.isRefreshing = false
            page = products.next_page_url?.lastOrNull()?.digitToIntOrNull()
            queryPageSize = products.per_page
            isLastPage = products.last_page == products.current_page
            when {
                products.data.isEmpty() -> binding.emptyDisclaimer.visible()
                else -> {
                    binding.emptyDisclaimer.invisible()
                }
            }
        }
        adapter.submitList(products?.data)
    }


    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= queryPageSize
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                page?.let {
                    viewModel.filterProducts(page = it, isRefresh = false)
                    isScrolling = false
                }
            }
        }


        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    companion object {
        fun newInstance(): FilterFragment {
            return FilterFragment()
        }
    }
}