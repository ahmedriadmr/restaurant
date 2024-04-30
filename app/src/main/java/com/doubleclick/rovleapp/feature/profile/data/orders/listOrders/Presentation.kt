package com.doubleclick.restaurant.feature.profile.data.orders.listOrders

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Presentation(
    val id: Int,
    val price: Double,
    val weight: Int
) : Parcelable