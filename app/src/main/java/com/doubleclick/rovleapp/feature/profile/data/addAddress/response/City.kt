package com.doubleclick.restaurant.feature.profile.data.addAddress.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val created_at: String,
    val id: Int,
    val name: String,
    val province_id: Int,
    val updated_at: String,
    val zip:String?
) : Parcelable