package com.doubleclick.rovleapp.feature.shop.cartDetails.data.filterCities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Province(
    val cities: List<City>,
    val country_id: Int,
    val created_at: String,
    val id: String,
    val name: String,
    val provinceCode: String,
    val updated_at: String,
    var isSelected: Boolean
) : Parcelable {
    companion object {
        val empty = Province(
            emptyList(),-1,"","","","","",false
        )

    }
}