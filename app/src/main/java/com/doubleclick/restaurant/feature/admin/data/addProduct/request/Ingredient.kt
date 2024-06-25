package com.doubleclick.restaurant.feature.admin.data.addProduct.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(var name: String = "") : Parcelable
