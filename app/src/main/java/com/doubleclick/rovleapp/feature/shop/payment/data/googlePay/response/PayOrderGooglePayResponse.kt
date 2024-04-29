package com.doubleclick.rovleapp.feature.shop.payment.data.googlePay.response

data class PayOrderGooglePayResponse(
    val `data`: PayOrderGooglePayData,
    val message: String,
    val status: Int
)