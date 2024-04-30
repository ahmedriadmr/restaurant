package com.doubleclick.restaurant.feature.passport.offers.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observeOrNull
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseDialogFragment
import com.doubleclick.restaurant.databinding.FragmentOffersBinding
import com.doubleclick.restaurant.feature.passport.PassportActivity
import com.doubleclick.restaurant.feature.passport.offers.data.Offers
import com.doubleclick.restaurant.feature.passport.offers.presentation.adapter.OffersAdapter
import com.doubleclick.restaurant.feature.shop.ShopActivity
import com.doubleclick.restaurant.feature.shop.showOffer.OfferClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OffersFragment : BaseDialogFragment(R.layout.fragment_offers) {

    private val binding by viewBinding(FragmentOffersBinding::bind)
    private val viewModel: OffersViewModel by viewModels()
    private val offersAdapter = OffersAdapter()

    private var offerClickListener: OfferClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OfferClickListener) {
            offerClickListener = context
        } else {
            throw RuntimeException("$context must implement OfferClickListener")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvOffers.adapter = offersAdapter

        with(viewModel) {
            observeOrNull(offers, ::renderState)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            listOffers()
        }
        offerClickListener = activity as? OfferClickListener
        offersAdapter.clickListenerApp = { providerId, offerId , discount->
            startActivity(
                Intent(requireActivity(), ShopActivity::class.java)
                    .putExtra("providerId", providerId)
                    .putExtra("offerId", offerId)
                    .putExtra("discount", discount)
            )
        }
        offersAdapter.clickListenerShop = {
            startActivity(Intent(requireActivity(), PassportActivity::class.java))
        }
    }

    private fun renderState(data: Offers?) {
        data?.let {
            binding.totalpoints.text = data.total_points
            offersAdapter.submitList(data.providers)
        }
    }
    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}