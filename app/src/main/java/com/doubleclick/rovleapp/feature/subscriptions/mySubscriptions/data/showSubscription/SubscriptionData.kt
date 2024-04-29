package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.showSubscription

import android.os.Parcelable
import com.doubleclick.rovleapp.feature.shop.response.CoffeeShop
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubscriptionData(
    val activation_date: String?,
    val address: String?,
    val created_at: String?,
    val delivery_type: String?,
    val device_id: Int?,
    val discount: String?,
    val email: String?,
    val end_at: String?,
    val id: String,
    val is_accepted: Int?,
    val locker_location: String?,
    val name: String?,
    val notes: String?,
    val paused:String?,
    val pause_date:String?,
    val periodicity: Int,
    val phone: String?,
    val plan: Plan?,
    val plan_id: String,
    val plan_size: PlanSize?,
    val plan_size_id: Int,
    val presentations: List<Presentation>?,
    val price: String?,
    val shipping: String?,
    val start_at: String?,
    val status: String?,
    val taxes: String?,
    val total: String?,
    val updated_at: String?,
    val user_id: Int?,
    val zip_code: String?,
    val coffee_shop: CoffeeShop?,
    val coffee_shop_id: String?
) : Parcelable {

    companion object {
        val empty = SubscriptionData(
            "",
            "",
            "",
            "",
            -1,
            "",
            "",
            "",
            "",
            -1,
            "",
            "",
            "",
            "",
            "",
            -1,
            "",
            Plan.empty,
            "",
            PlanSize.empty,
            -1,
            emptyList(),
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            -1,
            "",
            CoffeeShop.empty,
            "",
        )
    }
}