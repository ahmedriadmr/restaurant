package com.doubleclick.restaurant.feature.shop.showOffer

interface OfferClickListener {
    fun onOfferClicked(providerId: String, offerId: String, discount:String)
}