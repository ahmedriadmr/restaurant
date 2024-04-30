package com.doubleclick.restaurant.feature.shop.cartList

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.FragmentCartListBinding
import com.doubleclick.restaurant.feature.auth.AuthActivity
import com.doubleclick.restaurant.feature.profile.ProfileActivity
import com.doubleclick.restaurant.feature.shop.cart.CartViewModel
import com.doubleclick.restaurant.feature.shop.cart.response.getCart.NewCart
import com.doubleclick.restaurant.feature.shop.cart.response.updateCart.UpdateCart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartListFragment : BaseFragment(R.layout.fragment_cart_list) {

    private val binding by viewBinding(FragmentCartListBinding::bind)
    private val cartListAdapter = CartListAdapter()
    private val viewModel: CartViewModel by viewModels()
    private var adapterPosition = -1
    private var isApiCalled = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.carrito)
        initList()
        with(viewModel) {
            observe(getCart) { cart -> handleListCartResponse(cart) { cartListAdapter.submitList(null) } }
            observe(updateCart, ::handleUpdateCart)
            observe(deleteCart, ::handleDeleteCart)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
//            getCart()
        }
        if (!isApiCalled) {
            viewModel.resetCartPage()
            viewModel.getCart()
            isApiCalled = true
        }
        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.emptyTextView.visibility = View.GONE



        cartListAdapter.clickOrderNow = { order ->
            findNavController().navigate(
                CartListFragmentDirections.actionCartListFragmentToCartFragment(order)
            )
        }
    }

    override fun onPause() {
        super.onPause()
        // Reset the flag when the fragment is paused
        isApiCalled = false
    }
    private fun initList() {
        binding.rvMyCart.adapter = cartListAdapter
        binding.rvMyCart.addOnScrollListener(cartScrollListener)
        cartListAdapter.plusListener = { position, id , request ->
            adapterPosition = position
            if (request != null) {
                viewModel.updateCart(
                    id.cart_item_id,
                    request
                )
            }
        }
        cartListAdapter.minusListener = { position, id , request ->
            adapterPosition = position
            if (request != null) {
                viewModel.updateCart(
                    id.cart_item_id,
                    request
                )
            }
        }
        cartListAdapter.deleteListener =
            { position, itemId ->
                viewModel.adapterPosition = position
                viewModel.deleteCart(itemId)
            }
    }

    private fun handleListCartResponse(cart: List<NewCart>, refreshData: (() -> Unit)?) {
        var totalNumberOfCartItems = 0
        for (newCart in cart) {
            totalNumberOfCartItems += newCart.cart_items.size
        }
        lifecycleScope.launch { appSettingsSource.setCartInventory(totalNumberOfCartItems ) }
        when {
            cart.isEmpty() -> {
                refreshData?.invoke()
                binding.emptyTextView.visibility = View.VISIBLE
            }

            else -> {
                cartListAdapter.submitList(cart)
                binding.emptyTextView.visibility = View.GONE
            }
        }
    }

    private fun handleUpdateCart(data: UpdateCart) {
        cartListAdapter.updateCart3 = data
        cartListAdapter.notifyItemChanged(adapterPosition)
    }


    private fun handleDeleteCart(@Suppress("UNUSED_PARAMETER") data: Unit) {
        Toast.makeText(context, "you have successfully deleted this cart", Toast.LENGTH_SHORT).show()
        viewModel.getCart()
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

    var cartIsLoading = false
    var cartIsScrolling = false


    private val cartScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val cartLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val cartFirstVisibleItemPosition = cartLayoutManager.findFirstVisibleItemPosition()
            val cartVisibleItemCount = cartLayoutManager.childCount
            val cartTotalItemCount = cartLayoutManager.itemCount

            val cartIsNotLoadingAndNotLastPage = !cartIsLoading && !viewModel.cartIsLastPage
            val cartIsAtLastItem =
                cartFirstVisibleItemPosition + cartVisibleItemCount >= cartTotalItemCount
            val cartIsNotAtBeginning = cartFirstVisibleItemPosition >= 0
            val cartIsTotalMoreThanVisible =
                cartTotalItemCount >= viewModel.cartQueryPageSize
            val cartShouldPaginate =
                cartIsNotLoadingAndNotLastPage && cartIsAtLastItem && cartIsNotAtBeginning && cartIsTotalMoreThanVisible && cartIsScrolling
            if (cartShouldPaginate) {
                viewModel.getCart()
                cartIsScrolling = false
            } else {
                binding.rvMyCart.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                cartIsScrolling = true
            }
        }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}