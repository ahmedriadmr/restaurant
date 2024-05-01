package com.doubleclick.restaurant.feature.auth.login.data.responseNew

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Role(
    val created_at: String,
    val guard_name: String,
    val id: Int,
    val name: String,
    val permissions: List<PermissionX>,
    val pivot: PivotX,
    val updated_at: String
) : Parcelable