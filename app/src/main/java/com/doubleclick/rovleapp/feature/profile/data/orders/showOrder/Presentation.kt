package com.doubleclick.rovleapp.feature.profile.data.orders.showOrder

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Presentation(
    val id: Int,
    val price: Double,
    val weight: Int
) : Parcelable