package com.doubleclick.restaurant.feature.shop.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val address: String?,
    val altitude: String?,
    val city_id: Int,
    val code: String,
    val coffee_shops: List<CoffeeShop>?,
    val commercial_name: String?,
    val country_id: Int,
    val created_at: String?,
    val delivery_company: Int,
    val description: String?,
    val farm: String?,
    val grades: String?,
    val id: String,
    val manage_stock: Int,
    val nif: String?,
    val official_name: String?,
    val origin: String?,
    val origins: List<Origin>?,
    val owner_name: String?,
    val parent_id: String?,
    val phone: String?,
    val process: String?,
    val provider: Provider?,
    val provider_id: String?,
    val province_id: String?,
    val rate: String?,
    val rates_count: Int,
    val region: String?,
    val reviews_rate: String?,
    val sca_score: Int,
    val status: String?,
    val trade_name: String?,
    val updated_at: String?,
    val user_id: Int,
    val user_rate: String?,
    val variety: String?,
    val presentations: List<Presentation>?,
    val zip: String?
) : Parcelable {
    companion object {
        val empty = Product(
            "", "", -1, "", emptyList(), "", -1, "", -1,
            "", "", "", "", -1, "", "", "", emptyList(),"","", "", "",
            Provider.empty, "", "","",-1, "", "", -1, "", "", "", -1,"", "", emptyList(), ""
        )

    }
}