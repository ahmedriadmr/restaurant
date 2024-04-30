package com.doubleclick.restaurant.feature.subscriptions.providerList.data.listPlans

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlansData(
    val created_at: String,
    val description: String,
    val id: String,
    val name: String,
    val post_code: String?,
    val price_per_kilo: String,
    val provider: Provider,
    val provider_id: Int,
    val status: String,
    val updated_at: String
) : Parcelable {

    companion object {

        val empty = PlansData("", "", "", "","","", Provider.empty, -1, "", "")
    }
}