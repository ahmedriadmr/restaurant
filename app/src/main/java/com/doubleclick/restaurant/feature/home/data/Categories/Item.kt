package com.doubleclick.restaurant.feature.home.data.Categories

import android.os.Parcelable
import com.doubleclick.restaurant.feature.admin.data.addProduct.request.Ingredient
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val category_id: Int,
    val created_at: String,
    val description: String,
    val ingredients: List<Ingredient>?,
    val id: Int,
    val image: String?,
    val name: String,
    val sizes: List<Size>,
    val status: String,
    val updated_at: String,
    val vip: String
) : Parcelable