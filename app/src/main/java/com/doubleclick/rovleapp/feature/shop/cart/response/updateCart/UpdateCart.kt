package com.doubleclick.restaurant.feature.shop.cart.response.updateCart

import android.os.Parcelable
import com.doubleclick.restaurant.feature.shop.response.Presentation
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateCart(
    val created_at: String,
    val device_id: Int,
    val id: String,
    val notes: String?,
    val passport_offer_id: String?,
    val presentations: List<Presentation>,
    val product_id: Int,
    val provider_id: String,
    val updated_at: String,
    val user_id: Int
) : Parcelable {

    companion object {

        val empty = UpdateCart("",-1, "", "", "", emptyList(),-1,"",  "", -1)
    }
}