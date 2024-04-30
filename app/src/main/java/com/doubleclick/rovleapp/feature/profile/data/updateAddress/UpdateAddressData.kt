package com.doubleclick.restaurant.feature.profile.data.updateAddress

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateAddressData(
    val address: String,
    val city_id: String?,
    val country_id: String?,
    val created_at: String,
    val device_id: String?,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val province_id: String?,
    val updated_at: String,
    val user_id: Int,
    val user_name:String,
    val zip: String?
) : Parcelable