package com.doubleclick.restaurant.feature.admin.data.addStaff.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddStaffData(
    val address: String,
    val created_at: String,
    val email: String,
    val fcm_token: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val phone: String,
    val updated_at: String
) : Parcelable