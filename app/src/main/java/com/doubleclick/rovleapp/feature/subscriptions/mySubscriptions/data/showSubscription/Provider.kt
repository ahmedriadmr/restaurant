package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.showSubscription

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Provider(
    val address: String,
    val city_id: String,
    val commercial_name: String,
    val country_id: String,
    val created_at: String,
    val delivery_company: Int,
    val id: String,
    val manage_stock: Int,
    val nif: String,
    val official_name: String,
    val phone: String,
    val province_id: Int,
    val rate: String,
    val rates_count: Int,
    val updated_at: String,
    val user_id: String,
    val zip: String
) : Parcelable {

    companion object {

        val empty = Provider("", "", "", "", "", -1, "", -1, "", "", "", -1, "", -1, "", "", "")
    }
}