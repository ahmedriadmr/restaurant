package com.doubleclick.restaurant.feature.home.data.Categories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Size(
    val created_at: String,
    val id: String,
    val item_id: Int,
    val name: String,
    val price: Double,
    val status: String,
    val updated_at: String
) : Parcelable