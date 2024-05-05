package com.doubleclick.restaurant.feature.home.data.listCart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartData(
    val created_at: String,
    val id: String,
    val number: Int,
    val size: Size,
    val size_id: String,
    val updated_at: String,
    val user: User,
    val user_id: Int
) : Parcelable