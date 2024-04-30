package com.doubleclick.restaurant.feature.shop.cartDetails.data.filterCities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val created_at: String,
    val id: String,
    val name: String,
    val province_id: Int,
    val streets: List<Street>,
    val updated_at: String,
    val zip: String?,
    var isSelected: Boolean
) : Parcelable{
    companion object {
        val empty = City(
            ""  ,"","",-1,emptyList(),"","",false
        )

    }
}