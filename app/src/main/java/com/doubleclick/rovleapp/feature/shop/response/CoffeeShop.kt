package com.doubleclick.restaurant.feature.shop.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoffeeShop(
    val accepts_online_orders: Int,
    val accepts_orders_pick_up: Int,
    val address: String,
    val city_id: Int,
    val country_id: Int,
    val created_at: String,
    val default_for_sending:Int,
    val id: String,
    val latitude: String?,
    val longitude: String?,
    val name: String,
    val pivot: Pivot?,
    val post_code: String,
    val provider_id: Int,
    val province_id: Int,
    val updated_at: String,
    var isSelected: Boolean
) : Parcelable{

    companion object {
        val empty = CoffeeShop(
            -1,
            -1,
            "",
            -1,
            -1,
            "",
            -1,
            "",
            "",
            "",
            "",
            Pivot.empty,
            "",
            -1,
            -1,
            "",
            false


            )
    }
}
