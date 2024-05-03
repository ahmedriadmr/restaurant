package com.doubleclick.restaurant.feature.auth.login.data.responseNew

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Permission(
    val created_at: String,
    val guard_name: String,
    val id: Int,
    val name: String,
    val pivot: Pivot,
    val updated_at: String
): Parcelable