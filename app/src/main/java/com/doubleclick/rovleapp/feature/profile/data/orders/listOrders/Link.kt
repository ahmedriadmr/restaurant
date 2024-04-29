package com.doubleclick.rovleapp.feature.profile.data.orders.listOrders

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Link(
    val active: Boolean,
    val label: String,
    val url: String?
) : Parcelable