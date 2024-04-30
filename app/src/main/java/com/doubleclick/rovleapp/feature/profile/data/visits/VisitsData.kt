package com.doubleclick.restaurant.feature.profile.data.visits

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VisitsData(
    val created_at: String,
    val device_id: Int,
    val id: Int,
    val provider_id: Int,
    val provider_name: String,
    val updated_at: String,
    val user_id: Int,
    val visits: String
) : Parcelable