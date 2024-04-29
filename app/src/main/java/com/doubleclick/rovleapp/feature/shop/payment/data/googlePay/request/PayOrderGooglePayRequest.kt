package com.doubleclick.rovleapp.feature.shop.payment.data.googlePay.request

import android.os.Parcelable
import com.doubleclick.rovleapp.feature.shop.cartDetails.data.orderDetails.CartItem
import com.doubleclick.rovleapp.feature.subscriptions.paymentSubscription.data.googlePay.request.PaymentDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayOrderGooglePayRequest (
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
    val cart_items: List<CartItem>,
    val payment_details: PaymentDetails,
) : Parcelable