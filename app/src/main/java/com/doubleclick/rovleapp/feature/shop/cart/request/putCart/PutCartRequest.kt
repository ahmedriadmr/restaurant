package com.doubleclick.rovleapp.feature.shop.cart.request.putCart

data class PutCartRequest(
    val presentations: List<PresentationRequest>,
    val product_id: Int
)