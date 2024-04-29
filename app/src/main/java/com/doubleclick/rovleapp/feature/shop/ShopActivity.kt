package com.doubleclick.rovleapp.feature.shop

import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.platform.AuthPopup
import com.doubleclick.rovleapp.core.platform.BaseActivity
import com.doubleclick.rovleapp.databinding.ActivityShopBinding
import com.doubleclick.rovleapp.feature.shop.showOffer.OfferClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShopActivity : BaseActivity(), OfferClickListener {

    private lateinit var binding: ActivityShopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val isCart = intent.getBooleanExtra("cart", false)
        val navController = supportFragmentManager.findFragmentById(R.id.shop_nav_container)?.findNavController()

        navController?.let { nav ->
            val navGraph = nav.navInflater.inflate(R.navigation.shop_nav)

            if (isCart) {
                navGraph.setStartDestination(R.id.cartListFragment)
            } else {
                navGraph.setStartDestination(R.id.shopFragment)
            }

            nav.graph = navGraph
        }
    }
    override fun onDestroy() {
        // Dismiss any dialogs or popups here
        // Example: dialog?.dismiss()
        AuthPopup.dismiss()
        super.onDestroy()
    }
    override fun onOfferClicked(providerId: String, offerId: String, discount: String) {
        // Handle the offer click here
        Log.d("ShopActivity", "OfferId when navigating: $offerId")
        val navController = findNavController(this, R.id.shop_nav_container)
        val action = ShopFragmentDirections.actionShopFragmentToProductDetailsFragment(offerId)
        navController.navigate(action)
    }


}

