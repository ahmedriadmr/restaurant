package com.doubleclick.restaurant.feature.shop.cart.response.putCart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreData(
    val created_at: String,
    val device_id: Int,
    val id: String,
    val notes: String?,
    val passport_offer_id: String?,
    val product_id: Int,
    val provider_id: String,
    val updated_at: String,
    val user_id: Int
) : Parcelable {
    companion object {
        val empty = StoreData(
            "", -1,"", "","", -1,"", "", -1
        )
    }
}