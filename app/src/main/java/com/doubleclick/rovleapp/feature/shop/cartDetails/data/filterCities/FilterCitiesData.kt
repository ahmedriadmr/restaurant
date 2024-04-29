package com.doubleclick.rovleapp.feature.shop.cartDetails.data.filterCities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterCitiesData(
    val countryId: String,
    val created_at: String,
    val id: String,
    val name: String,
    val provinces: List<Province>,
    val updated_at: String,
    var isSelected: Boolean
): Parcelable {
    companion object {
        val empty = FilterCitiesData(
          ""  ,"","","",emptyList(),"",false
        )

    }
}