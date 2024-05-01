package com.doubleclick.rovleapp.feature.home.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Categories(
    val created_at: String,
    val id: Int,
    val image: String?,
    val items: List<Item>,
    val items_count: Int,
    val name: String,
    val status: String,
    val updated_at: String
) : Parcelable