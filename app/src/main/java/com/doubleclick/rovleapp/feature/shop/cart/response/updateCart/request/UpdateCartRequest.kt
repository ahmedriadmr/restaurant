package com.doubleclick.restaurant.feature.shop.cart.response.updateCart.request

import com.doubleclick.restaurant.feature.shop.cart.request.putCart.PresentationRequest

data class UpdateCartRequest (
    val presentations: MutableList<PresentationRequest>)