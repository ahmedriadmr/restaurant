package com.doubleclick.restaurant.feature.profile.data.addAddress.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressData(
    val address: String,
    val city: City,
    val city_id: String,
    val country: Country,
    val country_id: String,
    val created_at: String,
    val device_id:String?,
    val email: String?,
    val id: Int,
    val name: String,
    val phone: String?,
    val province: Province,
    val province_id: String,
    val updated_at: String,
    val user_id: Int,
    val user_name:String?,
    val zip: String
) : Parcelable