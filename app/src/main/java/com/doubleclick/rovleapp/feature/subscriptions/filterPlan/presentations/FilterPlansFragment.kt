package com.doubleclick.restaurant.feature.subscriptions.filterPlan.presentations

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.observeOrNull
import com.doubleclick.restaurant.core.extension.setInputFilter
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseDialogFragment
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.FragmentFilterPlansBinding
import com.doubleclick.restaurant.feature.auth.AuthActivity
import com.doubleclick.restaurant.feature.profile.ProfileActivity
import com.doubleclick.restaurant.feature.shop.searchProduct.data.providers.Providers
import com.doubleclick.restaurant.feature.shop.searchProduct.presentation.RangeInputFilter
import com.doubleclick.restaurant.feature.shop.searchProduct.presentation.SpinnerAdapterProviders
import com.doubleclick.restaurant.feature.subscriptions.SubscriptionsViewModel
import com.doubleclick.restaurant.feature.subscriptions.providerList.data.listPlans.PlansData
import com.doubleclick.restaurant.utils.collapse
import com.doubleclick.restaurant.utils.expand
import com.doubleclick.restaurant.views.rubberpicker.RubberRangePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterPlansFragment : BaseDialogFragment(R.layout.fragment_filter_plans) {

    private val binding by viewBinding(FragmentFilterPlansBinding::bind)
    private lateinit var navController: NavController
    private val viewModel: SubscriptionsViewModel by activityViewModels()
    private val providersAdapter = SpinnerAdapterProviders()
    private var isApiCalled = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.rvSelectProvider.adapter = providersAdapter
        binding.rvSelectProvider.addOnScrollListener(providersScrollListener)
        binding.header.title.text = getString(R.string.subscriptions_title)
        with(viewModel) {
            observeOrNull(filterPlan, ::renderState)
            observe(getProviders) { providers -> handleListProvidersResponse(providers) {providersAdapter.submitList(null)} }
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }

        onClick()
        if (!isApiCalled) {
            viewModel.resetProvidersPage()
            viewModel.getProviders()
            isApiCalled = true
        }

        binding.rubberRangePickerWeight.setOnRubberRangePickerChangeListener(object : RubberRangePicker.OnRubberRangePickerChangeListener {
            override fun onProgressChanged(
                rangePicker: RubberRangePicker, startValue: Int, endValue: Int, fromUser: Boolean
            ) {
                binding.minWeight.setText(startValue.toString())
                binding.maxWeight.setText(endValue.toString())
            }

            override fun onStartTrackingTouch(
                rangePicker: RubberRangePicker, isStartThumb: Boolean
            ) {

            }

            override fun onStopTrackingTouch(
                rangePicker: RubberRangePicker, isStartThumb: Boolean
            ) {
            }
        })

        binding.minWeight.setInputFilter(
            RangeInputFilter(
                binding.tlMin,
                0f,
                10000f,
                true,
                "Input value must be between ${0} and ${10000}"
            )
        )

        binding.maxWeight.setInputFilter(
            RangeInputFilter(
                binding.tlMax,
                0f,
                10000f,
                true,
                "Input value must be between ${0} and ${10000}"
            )
        )

        binding.minPrice.setInputFilter(
            RangeInputFilter(
                binding.tlMinPrice,
                0f,
                8000f,
                true,
                "Input value must be between ${0} and ${8000}"
            )
        )

        binding.maxPrice.setInputFilter(
            RangeInputFilter(
                binding.tlMaxPrice,
                0f,
                8000f,
                true,
                "Input value must be between ${0} and ${8000}"
            )
        )
        binding.rubberRangePickerPrice.setOnRubberRangePickerChangeListener(object : RubberRangePicker.OnRubberRangePickerChangeListener {
            override fun onProgressChanged(
                rangePicker: RubberRangePicker, startValue: Int, endValue: Int, fromUser: Boolean
            ) {

                binding.minPrice.setText(startValue.toString())
                binding.maxPrice.setText(endValue.toString())
            }

            override fun onStartTrackingTouch(
                rangePicker: RubberRangePicker, isStartThumb: Boolean
            ) {

            }

            override fun onStopTrackingTouch(
                rangePicker: RubberRangePicker, isStartThumb: Boolean
            ) {
            }

        })
        binding.confirmInformations.setOnClickListener {
            val name = when(binding.coffeeName.text.toString().isEmpty())
            {
                true -> null
                else -> binding.coffeeName.text.toString()
            }
            val weightMin = when(binding.llWeight.visibility == View.VISIBLE){
                true -> binding.minWeight.text.toString()
                false -> null
            }
            val weightMax =  when(binding.llWeight.visibility == View.VISIBLE){
                true -> binding.maxWeight.text.toString()
                false -> null
            }
            val priceMin = when(binding.llPrice.visibility == View.VISIBLE){
                true -> binding.minPrice.text.toString()
                false -> null
            }
            val priceMax = when(binding.llPrice.visibility == View.VISIBLE){
                true -> binding.maxPrice.text.toString()
                false -> null
            }
            viewModel.filterPlan(
                name,
                weightMin,
                weightMax,
                providersAdapter.currentList.filter { it.isSelected }.map { it.id },
                priceMin,
                priceMax
            )
        }
    }
    override fun onPause() {
        super.onPause()
        // Reset the flag when the fragment is paused
        isApiCalled = false
    }
    private fun onClick() {
        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.tvToster.setOnClickListener {
            if (binding.rvSelectProvider.isVisible) {
                collapse(binding.rvSelectProvider)
                binding.addToster.animate().rotation(0f).start()
            } else {
                expand(binding.rvSelectProvider)
                binding.addToster.animate().rotation(180f).start()
            }
        }

        binding.containerWeight.setOnClickListener {
            if (binding.llWeight.isVisible) {
                collapse(binding.llWeight)
            } else {
                expand(binding.llWeight)
            }
        }

        binding.tvPrice.setOnClickListener {
            if (binding.llPrice.isVisible) {
                collapse(binding.llPrice)
            } else {
                expand(binding.llPrice)
            }
        }
    }

    private fun handleListProvidersResponse(providers: List<Providers>, refreshData: (() -> Unit)?) {
        when {
            providers.isEmpty() -> refreshData?.invoke()
            else -> providersAdapter.submitList(providers)
        }
    }

    var providersIsLoading = false
    var providersIsScrolling = false


    private val providersScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val providersLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val providersFirstVisibleItemPosition = providersLayoutManager.findFirstVisibleItemPosition()
            val providersVisibleItemCount = providersLayoutManager.childCount
            val providersTotalItemCount = providersLayoutManager.itemCount

            val providersIsNotLoadingAndNotLastPage = !providersIsLoading && !viewModel.providersIsLastPage
            val providersIsAtLastItem =
                providersFirstVisibleItemPosition + providersVisibleItemCount >= providersTotalItemCount
            val providersIsNotAtBeginning = providersFirstVisibleItemPosition >= 0
            val providersIsTotalMoreThanVisible =
                providersTotalItemCount >= viewModel.providersQUERYPAGESIZE
            val providersShouldPaginate =
                providersIsNotLoadingAndNotLastPage && providersIsAtLastItem && providersIsNotAtBeginning && providersIsTotalMoreThanVisible && providersIsScrolling
            if (providersShouldPaginate) {
                viewModel.getProviders()
                providersIsScrolling = false
            } else {
                binding.rvSelectProvider.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                providersIsScrolling = true
            }
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

    private fun renderState(event: List<PlansData>?) {
        event?.let { requireActivity().onBackPressedDispatcher.onBackPressed() }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}