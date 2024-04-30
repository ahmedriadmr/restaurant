package com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.googlePay.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentDetails(
    val total_price: Double,
    val shipping: Double,
    val taxes: Double,
    val payment_id: String,
    val payment_method: String,
    val message: String,
    val status: String,
    ) : Parcelable {
        companion object{
            val empty = PaymentDetails(0.0,0.0,0.0,"","","","")
        }
    }
