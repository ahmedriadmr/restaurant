package com.doubleclick.rovleapp.feature.subscriptions

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.observe
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.functional.SortingOption
import com.doubleclick.rovleapp.core.functional.SortingOption.Companion.SORTING
import com.doubleclick.rovleapp.core.functional.SortingOption.Rating.toSortingOption
import com.doubleclick.rovleapp.core.platform.BaseFragment
import com.doubleclick.rovleapp.core.platform.local.UserAccess
import com.doubleclick.rovleapp.databinding.FragmentSubscriptionBinding
import com.doubleclick.rovleapp.feature.profile.ProfileActivity
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.listSubscriptions.MySubscriptionsData
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.presentation.MySubscriptionsAdapter
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.listPlans.PlansData
import com.doubleclick.rovleapp.feature.subscriptions.providerList.presentation.adapter.ProviderListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SubscriptionFragment : BaseFragment(R.layout.fragment_subscription) {


    private val binding by viewBinding(FragmentSubscriptionBinding::bind)
    private val mySubscriptionsAdapter = MySubscriptionsAdapter()
    private lateinit var providerListAdapter: ProviderListAdapter
    private val viewModel: SubscriptionsViewModel by activityViewModels()
    private val navArgs: SubscriptionFragmentArgs by navArgs()

    private lateinit var allPlans: List<PlansData>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        binding.header.title.text = getString(R.string.subscriptions_title)

        binding.header.back.setOnClickListener {
            navController.popBackStack()
        }

        with(viewModel) {
            observe(listPlans) { plans -> renderStatePlans(plans){providerListAdapter.submitList(null)}}
            observe(listSubscriptions) { subscriptions -> renderStateSubscriptions(subscriptions){mySubscriptionsAdapter.submitList(null)}}
            failure(failure, ::handleFailure)
            navArgs.providerId?.let { listPlans(it) }
            listSubscriptions()

        }



        providerListAdapter = ProviderListAdapter()
        binding.rvPlans.adapter = providerListAdapter
        allPlans = emptyList()
        binding.rvMysubscriptions.adapter = mySubscriptionsAdapter


        binding.filter.setOnClickListener {
            viewModel.clearFilterPlan()
            viewModel.selected = binding.providerList.getName().toString()
            navController.navigate(SubscriptionFragmentDirections.actionSubscribtionFragmentToFilterPlansFragment())
        }
        binding.sort.setOnClickListener {
            navController.navigate(SubscriptionFragmentDirections.actionSubscribtionFragmentToSortingInSubscriptionFragment())
        }

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(SORTING)?.observe(viewLifecycleOwner
        ) { result -> sortPlans(providerListAdapter.currentList, result.toSortingOption()) }

        binding.providerList.setOnClickListener {
            binding.rvMysubscriptions.visibility = View.GONE
            binding.emptyTextView.visibility = View.GONE
            binding.rvPlans.visibility = View.VISIBLE
            binding.rvPlans.adapter = providerListAdapter
            binding.sort.visibility = View.VISIBLE
            binding.filter.visibility = View.VISIBLE
        }

        if (navArgs.isEdit) {
            binding.providerList.performClick()
        }


        providerListAdapter.clickPlan = { planId ->
            navController.navigate(
                SubscriptionFragmentDirections.actionSubscribtionFragmentToShowPlanFragment(
                    navArgs.subscriptionId,
                    planId
                )
            )
        }

        mySubscriptionsAdapter.clickShowSubscription = { subscription ->
            navController.navigate(
                SubscriptionFragmentDirections.actionSubscribtionFragmentToShowMySubscriptionFragment(
                    subscription
                )
            )
        }
        binding.header.back.setOnClickListener {
            (requireActivity() as SubscriptionActivity).onBackPressedDispatcher.onBackPressed()
        }
    }


    private fun renderStatePlans(plans: List<PlansData>, refreshData: (() -> Unit)?) {
        allPlans = plans
        when {
            plans.isEmpty() -> refreshData?.invoke()
            else -> resetAdapter(data = plans)
        }
    }

    private fun sortPlans(plans: List<PlansData>, sortingOption: SortingOption) {
        when (sortingOption) {
            SortingOption.Rating -> resetAdapter(plans.sortedByDescending { it.provider.rate?.toDoubleOrNull() ?: 0.0 })
            SortingOption.PriceAsc -> resetAdapter(plans.sortedBy { it.price_per_kilo.toDouble() })
            SortingOption.PriceDes -> resetAdapter(plans.sortedByDescending { it.price_per_kilo.toDouble() })
            SortingOption.Periodicity -> resetAdapter(viewModel.listPlans.value)
            SortingOption.Sizes -> resetAdapter(viewModel.listPlans.value)
        }
    }

    private fun resetAdapter(data: List<PlansData>) {
        providerListAdapter.submitList(null)
        providerListAdapter.submitList(data)
    }

    private fun renderStateSubscriptions(subscriptions: List<MySubscriptionsData>, refreshData: (() -> Unit)?) {
        when {

            subscriptions.isEmpty() -> {
                refreshData?.invoke()
                binding.providerList.performClick()
            }

            else -> {
                mySubscriptionsAdapter.submitList(subscriptions)
                binding.emptyTextView.visibility = View.GONE
            }
        }
        binding.mySubscriptions.setOnClickListener {
            binding.sort.visibility = View.GONE
            binding.filter.visibility = View.GONE
            binding.sort.visibility = View.GONE
            if (subscriptions.isEmpty()) {
                binding.emptyTextView.visibility = View.VISIBLE
                binding.rvMysubscriptions.visibility = View.GONE
                binding.rvPlans.visibility = View.GONE
            } else {
                binding.emptyTextView.visibility = View.GONE
                binding.rvMysubscriptions.visibility = View.VISIBLE
                binding.rvPlans.visibility = View.GONE
            }
        }
    }


    override fun renderAuthenticating(user: UserAccess?) {
        super.renderAuthenticating(user)

        binding.header.photo.load(user?.image) {
            scale(Scale.FILL)
        }

        binding.header.photo.setOnClickListener { startActivity(Intent(requireActivity(), ProfileActivity::class.java)) }
    }
}