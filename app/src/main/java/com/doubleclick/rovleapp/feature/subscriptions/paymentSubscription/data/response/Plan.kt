package com.doubleclick.restaurant.feature.subscriptions.paymentSubscription.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plan(
    val created_at: String,
    val description: String,
    val id: Int,
    val name: String,
    val provider: Provider,
    val provider_id: Int,
    val status: String,
    val updated_at: String
) : Parcelable