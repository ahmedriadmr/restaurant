package com.doubleclick.restaurant.feature.subscriptions.providerList.data.showPlan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pivot(
    val coffee_shop_id: Int,
    val plan_id: String
) : Parcelable{

    companion object {
        val empty = Pivot(
            -1,
            "",
            )
    }
}