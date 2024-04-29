package com.doubleclick.rovleapp.feature.profile.data.orders.showOrder

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowOrderData(
    val address: String,
    val coffee_shop_id: String?,
    val created_at: String,
    val delivery_type: String,
    val device_id: String?,
    val email: String,
    val id: String,
    val locker_location: String?,
    val name: String,
    val notes: String?,
    val phone: String,
    val products: List<Product>,
    val provider: Provider?,
    val provider_id: String,
    val shipping: String?,
    val status: String,
    val taxes: String?,
    val total: String,
    val updated_at: String,
    val user_id: Int,
    val zip_code: String
) : Parcelable {
    companion object {
        val empty = ShowOrderData(
            "", "", "", "", "", "", "",  "", "", "", "", emptyList(), Provider.empty, "", "", "", "", "", "", -1, ""
        )
    }
}