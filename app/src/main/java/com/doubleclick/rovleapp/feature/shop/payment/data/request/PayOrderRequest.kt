package com.doubleclick.rovleapp.feature.shop.payment.data.request

data class PayOrderRequest(
    val card_number: String,
    val card_expiration_date: String,
    val card_cvx: String,
    val provider_id: String,
    val delivery_type: String,
    val zip_code: String,
    val name: String,
    val phone: String,
    val email: String,
    val address: String,
    val coffee_shop_id: String,
    val locker_id: String,
    val note: String,
    val payment_method: String
)