package com.doubleclick.restaurant.feature.admin.data.listItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemsData(
    val category: Category,
    val category_id: Int,
    val created_at: String,
    val description: String,
    val id: Int,
    val image: String?,
    val name: String,
    val sizes: List<Size>,
    val status: String,
    val updated_at: String,
    val vip: String
) : Parcelable