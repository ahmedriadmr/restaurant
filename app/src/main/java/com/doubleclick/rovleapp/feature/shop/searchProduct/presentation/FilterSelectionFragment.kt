package com.doubleclick.rovleapp.feature.shop.searchProduct.presentation

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
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.loading
import com.doubleclick.rovleapp.core.extension.observe
import com.doubleclick.rovleapp.core.extension.observeOrNull
import com.doubleclick.rovleapp.core.extension.setInputFilter
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.functional.ProgressHandler
import com.doubleclick.rovleapp.core.platform.BaseDialogFragment
import com.doubleclick.rovleapp.core.platform.local.UserAccess
import com.doubleclick.rovleapp.databinding.FragmentFilterBinding
import com.doubleclick.rovleapp.feature.auth.AuthActivity
import com.doubleclick.rovleapp.feature.profile.ProfileActivity
import com.doubleclick.rovleapp.feature.shop.ShopViewModel
import com.doubleclick.rovleapp.feature.shop.response.ProductData
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.origins.Origins
import com.doubleclick.rovleapp.feature.shop.searchProduct.data.providers.Providers
import com.doubleclick.rovleapp.utils.collapse
import com.doubleclick.rovleapp.utils.expand
import com.doubleclick.rovleapp.views.rubberpicker.RubberRangePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FilterSelectionFragment : BaseDialogFragment(R.layout.fragment_filter) {
    var initialState = true

    private val binding by viewBinding(FragmentFilterBinding::bind)
    private lateinit var navController: NavController
    private val viewModel: ShopViewModel by activityViewModels()
    private val providersAdapter = SpinnerAdapterProviders()
    private val originsAdapter = SpinnerAdapterOrigins()
    private var isApiCalled = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.header.title.text = getString(R.string.store_title)
        binding.rvSelectProvider.adapter = providersAdapter
        binding.rvSelectProvider.addOnScrollListener(providersScrollListener)
        binding.rvSelectOrigen.adapter = originsAdapter
        binding.rvSelectOrigen.addOnScrollListener(originsScrollListener)
        with(viewModel) {
            observeOrNull(filter, ::renderState)
            observe(getProviders) { providers -> handleListProvidersResponse(providers){providersAdapter.submitList(null) }}
            observe(getOrigins) { origins -> handleListOriginsResponse(origins){originsAdapter.submitList(null) }}
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)

        }
        onClick()
        if (!isApiCalled) {
            viewModel.resetProvidersAndOriginsPage()
            viewModel.getProviders()
            viewModel.getOrigins()
            isApiCalled = true
        }

        binding.rubberRangePickerSCA.setOnRubberRangePickerChangeListener(object : RubberRangePicker.OnRubberRangePickerChangeListener {
            override fun onProgressChanged(
                rangePicker: RubberRangePicker, startValue: Int, endValue: Int, fromUser: Boolean
            ) {
                binding.minSca.setText(startValue.toString())
                binding.maxSca.setText(endValue.toString())
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

        binding.minSca.setInputFilter(
            RangeInputFilter(
                binding.tlMin,
                80f,
                100f,
                true,
                "Input value must be between ${80} and ${100}"
            )
        )

        binding.maxSca.setInputFilter(
            RangeInputFilter(
                binding.tlMax,
                80f,
                100f,
                true,
                "Input value must be between ${80} and ${100}"
            )
        )

        binding.minAltitude.setInputFilter(
            RangeInputFilter(
                binding.tlMinAlt,
                0f,
                8000f,
                true,
                "Input value must be between ${0} and ${8000}"
            )
        )

        binding.maxAltitude.setInputFilter(
            RangeInputFilter(
                binding.tlMaxAlt,
                0f,
                8000f,
                true,
                "Input value must be between ${0} and ${8000}"
            )
        )
        binding.rubberRangePickerAltitud.setOnRubberRangePickerChangeListener(object : RubberRangePicker.OnRubberRangePickerChangeListener {
            override fun onProgressChanged(
                rangePicker: RubberRangePicker, startValue: Int, endValue: Int, fromUser: Boolean
            ) {

                binding.minAltitude.setText(startValue.toString())
                binding.maxAltitude.setText(endValue.toString())
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
            val query = binding.coffeeName.text.toString()
            val scaMin = binding.minSca.text.toString()
            val scaMax = binding.maxSca.text.toString()
            val altMin = binding.minAltitude.text.toString()
            val altMax = binding.maxAltitude.text.toString()
            viewModel.filterProducts(
                query,
                originsAdapter.currentList.filter { it.isSelected }.map { it.id },
                scaMin,
                scaMax,
                providersAdapter.currentList.filter { it.isSelected }.map { it.id },
                altMin,
                altMax,
                "",
                "",
                1,
                true
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
        binding.tvOrigen.setOnClickListener {
            if (binding.rvSelectOrigen.isVisible) {
                binding.rvSelectOrigen.visibility = View.GONE
                binding.addOrigen.animate().rotation(0f).start()
            } else {
                binding.rvSelectOrigen.visibility = View.VISIBLE
                binding.addOrigen.animate().rotation(180f).start()
            }
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

        binding.containerSca.setOnClickListener {
            if (binding.llSca.isVisible) {
                collapse(binding.llSca)
            } else {
                expand(binding.llSca)
            }
        }

        binding.tvAltitud.setOnClickListener {
            if (binding.llAltitud.isVisible) {
                collapse(binding.llAltitud)
            } else {
                expand(binding.llAltitud)
            }
        }
    }

    private fun handleListProvidersResponse(providers: List<Providers>, refreshData: (() -> Unit)?) {
        when {
            providers.isEmpty() -> refreshData?.invoke()
            else -> providersAdapter.submitList(providers)
        }
    }

    private fun handleListOriginsResponse(origins: List<Origins>, refreshData: (() -> Unit)?) {
        when {
            origins.isEmpty() -> refreshData?.invoke()
            else -> originsAdapter.submitList(origins)
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

    var originsIsLoading = false
    var originsIsScrolling = false


    private val originsScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val originsLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val originsFirstVisibleItemPosition =originsLayoutManager.findFirstVisibleItemPosition()
            val originsVisibleItemCount = originsLayoutManager.childCount
            val originsTotalItemCount = originsLayoutManager.itemCount

            val originsIsNotLoadingAndNotLastPage = !originsIsLoading && !viewModel.originsIsLastPage
            val originsIsAtLastItem =
                originsFirstVisibleItemPosition + originsVisibleItemCount >= originsTotalItemCount
            val originsIsNotAtBeginning = originsFirstVisibleItemPosition >= 0
            val originsIsTotalMoreThanVisible =
                originsTotalItemCount >= viewModel.originsQUERYPAGESIZE
            val originsShouldPaginate =
                originsIsNotLoadingAndNotLastPage && originsIsAtLastItem && originsIsNotAtBeginning && originsIsTotalMoreThanVisible && originsIsScrolling
            if (originsShouldPaginate) {
                viewModel.getOrigins()
                originsIsScrolling = false
            } else {
                binding.rvSelectOrigen.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                originsIsScrolling = true
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
    private fun renderState(event: ProductData?) {
        if (!initialState) requireActivity().onBackPressedDispatcher.onBackPressed()
        initialState = false
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}