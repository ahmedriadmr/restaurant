package com.doubleclick.restaurant.feature.profile.data.points

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointsData(
    val created_at: String,
    val device_id: Int,
    val id: String,
    val points: String,
    val provider_id: Int,
    val provider_name: String,
    val updated_at: String,
    val user_id: Int
) : Parcelable {


    companion object {
        val empty = PointsData("", -1, "", "", -1, "", "", -1)
    }
}