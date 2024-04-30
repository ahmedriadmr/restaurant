package com.doubleclick.restaurant.feature.auth.login.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Role(
    val created_at: String,
    val guard_name: String,
    val id: String,
    val name: String,
    val owner_id: Int,
    val permissions: List<Permission>,
    val pivot: PivotX,
    val updated_at: String
) : Parcelable