package com.doubleclick.restaurant.feature.auth.signup.data.responseNew

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pivot(
    val model_id: Int,
    val model_type: String,
    val role_id: Int
) : Parcelable