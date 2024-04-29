package com.doubleclick.rovleapp.feature.shop.showOffer

interface OfferClickListener {
    fun onOfferClicked(providerId: String, offerId: String, discount:String)
}