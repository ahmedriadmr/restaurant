package com.doubleclick.restaurant.feature.admin.data.listItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Size(
    val created_at: String,
    val id: Int,
    val item_id: Int,
    val name: String,
    val price: Double,
    val status: String,
    val updated_at: String
) : Parcelable