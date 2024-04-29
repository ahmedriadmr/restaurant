package com.doubleclick.rovleapp.feature.auth.login.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Permission(
    val category: String,
    val created_at: String,
    val guard_name: String,
    val id: String,
    val name: String,
    val pivot: Pivot,
    val title: String,
    val updated_at: String
) : Parcelable