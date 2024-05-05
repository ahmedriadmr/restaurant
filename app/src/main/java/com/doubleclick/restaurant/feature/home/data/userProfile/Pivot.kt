package com.doubleclick.restaurant.feature.home.data.userProfile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pivot(
    val model_id: Int,
    val model_type: String,
    val role_id: Int
) : Parcelable