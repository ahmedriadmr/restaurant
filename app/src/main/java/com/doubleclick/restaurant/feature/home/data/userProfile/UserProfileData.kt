package com.doubleclick.restaurant.feature.home.data.userProfile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfileData(
    val address: String,
    val created_at: String,
    val device_token: String,
    val email: String,
    val email_verified_at: String?,
    val fcm_token: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val otp_code: String,
    val phone: String,
    val roles: List<Role>,
    val status: String,
    val updated_at: String
) : Parcelable