package com.doubleclick.restaurant.feature.profile.data.rates

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingData(
    val product_rate: String,
    val provider_rate: String,
    val user_rate: String?
) : Parcelable