package com.doubleclick.rovleapp.feature.shop.cartDetails.data.filterCities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Street(
    val city_id: Int,
    val created_at: String,
    val id: Int,
    val name: String,
    val updated_at: String,
    val zip: String
) : Parcelable