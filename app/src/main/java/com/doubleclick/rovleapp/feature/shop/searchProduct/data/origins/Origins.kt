package com.doubleclick.restaurant.feature.shop.searchProduct.data.origins

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Origins(
    val created_at: String,
    val id: Int,
    val name: String,
    val provider_id: Int,
    val updated_at: String,
    var isSelected: Boolean
) : Parcelable