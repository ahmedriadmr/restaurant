package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.showSubscription

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanSize(
    val created_at: String,
    val id: String,
    val plan_id: String,
    val price: String,
    val size: Size?,
    val size_id: Int,
    val status: String,
    val updated_at: String
) : Parcelable {

    companion object {

        val empty = PlanSize("", "", "", "", Size.empty, -1, "", "")
    }
}