package com.doubleclick.rovleapp.feature.subscriptions.providerList.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.feature.subscriptions.providerList.presentation.adapter.PlansAdapter
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.loading
import com.doubleclick.rovleapp.core.extension.observeOrNull
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.functional.ProgressHandler
import com.doubleclick.rovleapp.core.platform.BaseFragment
import com.doubleclick.rovleapp.core.platform.local.UserAccess
import com.doubleclick.rovleapp.databinding.FragmentShowPlanBinding
import com.doubleclick.rovleapp.feature.auth.AuthActivity
import com.doubleclick.rovleapp.feature.profile.ProfileActivity
import com.doubleclick.rovleapp.feature.shop.productDetails.presentation.adapter.CoffeeShopsAdapter
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.showPlan.PlanDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowPlanFragment : BaseFragment(R.layout.fragment_show_plan) {

    private val binding by viewBinding(FragmentShowPlanBinding::bind)
    private val plansAdapter = PlansAdapter()
    private val ourShopAdapter = CoffeeShopsAdapter()
    private val viewModel: ShowPlanViewModel by viewModels()
    private val navArgs: ShowPlanFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.subscriptions_title)

        binding.rvOurPlans.adapter = plansAdapter
        binding.rvOurShop.adapter = ourShopAdapter

        with(viewModel) {
            observeOrNull(showPlan, ::renderPlan)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            navArgs.planId?.let { getShowPlan(it) }
        }


        binding.header.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun renderPlan(data: PlanDetails?) {
        data?.let { plan ->
            plan.sizes.let {
                plansAdapter.showPlanCollection = it
                plansAdapter.showLeastSize()
            }
            binding.address.text = plan.provider.address
            ourShopAdapter.submitList(plan.coffee_shops)
            binding.planName.text = plan.name
            binding.providerName.text = plan.provider.commercial_name
            binding.description.text = plan.description
            binding.aboutPlanName.text = getString(R.string.about_plan_name_text, plan.name)
            plansAdapter.planName = plan.name
            plansAdapter.planDescription = plan.description
            plansAdapter.clickSize = { _, size ->
                findNavController().navigate(
                    ShowPlanFragmentDirections.actionShowPlanFragmentToPlanInformationFragment(
                        subscriptionId = navArgs.subscriptionId,
                        plan = plan.copy(sizes = size),
                        title = "Plan information",
                    )
                )
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
            }
            else{
                binding.header.photo.setOnClickListener { startActivity(AuthActivity.callingIntent(requireActivity())) }}
        }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}