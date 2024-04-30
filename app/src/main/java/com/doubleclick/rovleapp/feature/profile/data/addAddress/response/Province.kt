package com.doubleclick.restaurant.feature.profile.data.addAddress.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Province(
    val country_id: Int,
    val created_at: String,
    val id: Int,
    val name: String,
    val provinceCode:String,
    val updated_at: String
) : Parcelable