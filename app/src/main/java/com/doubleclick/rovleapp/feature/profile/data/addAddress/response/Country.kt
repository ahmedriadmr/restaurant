package com.doubleclick.restaurant.feature.profile.data.addAddress.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val countryId:String,
    val created_at: String,
    val id: Int,
    val name: String,
    val updated_at: String
) : Parcelable