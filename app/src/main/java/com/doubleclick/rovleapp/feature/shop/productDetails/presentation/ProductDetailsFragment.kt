package com.doubleclick.rovleapp.feature.shop.productDetails.presentation


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.loading
import com.doubleclick.rovleapp.core.extension.observe
import com.doubleclick.rovleapp.core.extension.observeOrNull
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.functional.ProgressHandler
import com.doubleclick.rovleapp.core.platform.BaseDialogFragment
import com.doubleclick.rovleapp.core.platform.local.UserAccess
import com.doubleclick.rovleapp.databinding.FragmentProductDetailsBinding
import com.doubleclick.rovleapp.dialog.DialogProductDetails
import com.doubleclick.rovleapp.feature.auth.AuthActivity
import com.doubleclick.rovleapp.feature.profile.ProfileActivity
import com.doubleclick.rovleapp.feature.shop.cart.response.putCart.StoreData
import com.doubleclick.rovleapp.feature.shop.productDetails.presentation.adapter.PackageAdapter
import com.doubleclick.rovleapp.feature.shop.response.Product
import com.doubleclick.rovleapp.feature.shop.showOffer.data.ShowOfferData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : BaseDialogFragment(R.layout.fragment_product_details) {


    private val navArgs: ProductDetailsFragmentArgs by navArgs()
    private val viewModel: ShowProductViewModel by viewModels()
    private var showProductAdapter: PackageAdapter = PackageAdapter()
    private val binding by viewBinding(FragmentProductDetailsBinding::bind)
    private var shouldBack = false
    var providerId: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.adding_to_cart_title)

        binding.rvVariety.adapter = showProductAdapter
        showProductAdapter.cartUpdated = { totalPrice ->
            val totalPriceText = getString(R.string.total_price_format, totalPrice, getString(R.string.euro_sign))
            binding.totalprice.text = totalPriceText
        }
        Log.d("ProductDetailsFragment", "OfferId from navArgs: ${navArgs.offerId}")
        with(viewModel) {
            observeOrNull(showProduct, ::handleShowProduct)
            observeOrNull(showOffer, ::handleShowOffer)
            observe(putCartWithOffer, ::handlePutCartWithOffer)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getShowProduct(navArgs.id)
        }

        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }



        binding.card.setOnClickListener {
            val confirmedList = showProductAdapter.getConfirmedList()

            if (confirmedList != null) {
                DialogProductDetails.showTwoButtonPopup(
                    requireContext(),
                    "Quieres pagar ahora o volver a la tienda?",
                    {
                        with(activity?.intent?.getStringExtra("offerId")) {
                            shouldBack = true
                            if (this.isNullOrEmpty()) {
                                viewModel.putCartWithOrWithoutOffer(confirmedList, null)
                            } else {
                                activity?.intent?.getStringExtra("offerId")?.let { it1 -> viewModel.putCartWithOrWithoutOffer(confirmedList, it1) }
                            }
                        }
                    },


                    {
                        with(activity?.intent?.getStringExtra("offerId")) {
                            shouldBack = false

                            if (this.isNullOrEmpty()) {
                                viewModel.putCartWithOrWithoutOffer(confirmedList, null)
                            } else {
                                activity?.intent?.getStringExtra("offerId")?.let { it1 -> viewModel.putCartWithOrWithoutOffer(confirmedList, it1) }
                            }
                        }
                    }
                )
            } else {
                Toast.makeText(requireContext(), "Please choose items before confirming.", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun handlePutCartWithOffer(@Suppress("UNUSED_PARAMETER") data: StoreData) {
        updateCartInventory()
        delegateNavigation()
    }

    private fun updateCartInventory(){
        activity?.lifecycleScope?.launch {
            appSettingsSource.setCartInventory(appSettingsSource.cartInventory().first() + 1)
        }
    }

    private fun delegateNavigation() {
        when (shouldBack) {
            true -> findNavController().navigate(ProductDetailsFragmentDirections.actionProductDetailsFragmentToShopFragment())
            false -> findNavController().navigate(ProductDetailsFragmentDirections.actionProductDetailsFragmentToCartListFragment())
        }
    }


    private fun handleShowProduct(data: Product?) {
        data?.let {
            if (data.presentations.isNullOrEmpty()) {
                binding.llPresentations.visibility = View.GONE
            } else {
                binding.llPresentations.visibility = View.VISIBLE
                data.presentations.let { showProductAdapter.showProductCollection = it }
            }

            binding.coffeeName.text = data.commercial_name
            binding.rostedBy.text = data.provider?.commercial_name
            val scaString = data.sca_score.toString()
            binding.sca.text = getString(R.string.scascore, scaString)
            providerId = data.provider_id

            activity?.intent?.run {
                val offerId = getStringExtra("offerId")
                val providerId = getStringExtra("providerId")

                if ((offerId != null) && (providerId != null) && (data.provider_id.toString() == providerId)) {
                    viewModel.getShowOffer(offerId)
                    binding.discount.visibility = View.VISIBLE
                } else {
                    binding.discount.visibility = View.GONE
                }

            }

        }
    }

    private fun handleShowOffer(data: ShowOfferData?) {
        showProductAdapter.implementShowOfferData(data)
        if (data != null) {
            binding.discount.text = getString(R.string.discount_text,data.discount)
        }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
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