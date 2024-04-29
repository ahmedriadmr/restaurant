package com.doubleclick.rovleapp.feature.auth.login.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pivot(
    val permission_id: Int,
    val role_id: Int
) : Parcelable