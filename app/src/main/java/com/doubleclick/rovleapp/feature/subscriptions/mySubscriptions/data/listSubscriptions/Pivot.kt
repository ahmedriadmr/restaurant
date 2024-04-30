package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.listSubscriptions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pivot(
    val plan_subscription_id: Int,
    val presentation_id: Int
) : Parcelable