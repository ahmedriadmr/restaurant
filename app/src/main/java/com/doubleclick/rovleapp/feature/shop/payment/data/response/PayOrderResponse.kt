package com.doubleclick.rovleapp.feature.shop.payment.data.response

data class PayOrderResponse(
    val `data`: PayOrderData,
    val message: String,
    val status: Int
)