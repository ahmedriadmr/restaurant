package com.doubleclick.rovleapp.feature.shop.cart.response.updateCart.request

import com.doubleclick.rovleapp.feature.shop.cart.request.putCart.PresentationRequest

data class UpdateCartRequest (
    val presentations: MutableList<PresentationRequest>)