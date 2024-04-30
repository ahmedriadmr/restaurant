package com.doubleclick.restaurant.feature.shop.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Origin(
    val created_at: String,
    val id: Int,
    val name: String,
    val pivot: Pivot,
    val provider_id: Int,
    val updated_at: String
) : Parcelable