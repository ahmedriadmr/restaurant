package com.doubleclick.restaurant.feature.admin.data.listItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val created_at: String,
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val updated_at: String
) : Parcelable