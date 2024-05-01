package com.doubleclick.restaurant.feature.auth.login.data.responseNew

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pivot(
    val permission_id: Int,
    val role_id: Int
) : Parcelable