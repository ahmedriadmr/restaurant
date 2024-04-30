package com.doubleclick.restaurant.feature.shop.productDetails.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.observeOrNull
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.platform.BaseDialogFragment
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.FragmentVerFichaBinding
import com.doubleclick.restaurant.feature.auth.AuthActivity
import com.doubleclick.restaurant.feature.profile.ProfileActivity
import com.doubleclick.restaurant.feature.shop.productDetails.presentation.adapter.CoffeeShopsAdapter
import com.doubleclick.restaurant.feature.shop.productDetails.presentation.adapter.VerFichaAdapter
import com.doubleclick.restaurant.feature.shop.response.Product
import com.doubleclick.restaurant.feature.shop.showOffer.data.ShowOfferData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerFichaFragment : BaseDialogFragment(R.layout.fragment_ver_ficha) {


    private val navArgs: VerFichaFragmentArgs by navArgs()
    private val viewModel: ShowProductViewModel by viewModels()
    private var verFichaAdapter: VerFichaAdapter = VerFichaAdapter()
    private val binding by viewBinding(FragmentVerFichaBinding::bind)
    private val coffeeShopAdapter = CoffeeShopsAdapter()
    var providerId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.detalles_del_producto)

        binding.rvVariety.adapter = verFichaAdapter
        binding.rvOurShop.adapter = coffeeShopAdapter
        with(viewModel) {
            observeOrNull(showProduct, ::handleShowProduct)
            observeOrNull(showOffer, ::handleShowOffer)
            failure(failure, ::handleFailure)
            getShowProduct(navArgs.id)
        }

        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.card.setOnClickListener {
            findNavController().navigate(
                VerFichaFragmentDirections.actionVerFichaFragmentToProductDetailsFragment(
                    navArgs.id , navArgs.offerId
                )
            )
        }
    }


    private fun handleShowProduct(data: Product?) {
        data?.let {
            if(data.coffee_shops.isNullOrEmpty()){
                binding.llCoffeeShops.visibility = View.GONE
            } else {
                binding.llCoffeeShops.visibility = View.VISIBLE
                coffeeShopAdapter.submitList(data.coffee_shops)
            }

            if(data.presentations.isNullOrEmpty()){
                binding.rvVariety.visibility = View.GONE
            } else {
                binding.rvVariety.visibility = View.VISIBLE
                data.presentations.let { verFichaAdapter.submitList(data.presentations) }
            }


            if (data.commercial_name.isNullOrEmpty()) {
                binding.llCoffeeName.visibility = View.GONE
            } else {
                binding.llCoffeeName.visibility = View.VISIBLE
                binding.coffeeName.text = "${data.commercial_name}"
            }

            if (data.provider?.commercial_name.isNullOrEmpty()) {
                binding.llRoastedby.visibility = View.GONE
            } else {
                binding.llRoastedby.visibility = View.VISIBLE
                binding.rostedBy.text = getString(R.string.roasted_by_description, data.provider?.commercial_name ?: "")
            }

            val scaString = data.sca_score.toString()
            binding.sca.text = getString(R.string.scascore, scaString)
            providerId = data.provider_id

            if (data.description.isNullOrEmpty()) {
                binding.llDescription.visibility = View.GONE
            } else {
                binding.llDescription.visibility = View.VISIBLE
                binding.description.text = getString(R.string.description_text, data.description)
            }

            if (data.region.isNullOrEmpty()) {
                binding.llRegion.visibility = View.GONE
            } else {
                binding.llRegion.visibility = View.VISIBLE
                binding.region.text = getString(R.string.region_text, data.region)
            }


            if (data.origins?.joinToString(", "){it.name}?.isEmpty() == true) {
                binding.llOrigin.visibility = View.GONE
            } else {
                binding.llOrigin.visibility = View.VISIBLE
                binding.origin.text = getString(R.string.origin_text, data.origins?.joinToString(", ") { it.name } ?: "")
            }

            if (data.farm.isNullOrEmpty()) {
                binding.llFarm.visibility = View.GONE
            } else {
                binding.llFarm.visibility = View.VISIBLE
                binding.farm.text = getString(R.string.farm_text, data.farm)
            }

            if (data.altitude.isNullOrEmpty()) {
                binding.llAltitude.visibility = View.GONE
            } else {
                binding.llAltitude.visibility = View.VISIBLE
                binding.altitude.text = getString(R.string.altitude_text, data.altitude)
            }



            activity?.intent?.getStringExtra("offerId")?.let { offerId ->
                viewModel.getShowOffer(offerId)
            }
        }


    }

    private fun handleShowOffer(data: ShowOfferData?) {
        verFichaAdapter.implementShowOfferData(data)
    }

    override fun renderFeatureFailure(message: String?) {
        super.renderFeatureFailure(message)
        activity?.intent?.removeExtra("offerId")
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
}