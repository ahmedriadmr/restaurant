package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.showSubscription

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Presentation(
    val created_at: String,
    val id: Int,
    val plan_subscription_id: Int,
    val presentation_id: Int,
    val quantity: String,
    val updated_at: String
) : Parcelable