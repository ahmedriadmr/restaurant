package com.doubleclick.restaurant.feature.subscriptions.providerList.data.showPlan

import android.os.Parcelable
import com.doubleclick.restaurant.feature.shop.response.CoffeeShop
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanDetails(
    val coffee_shops: List<CoffeeShop>,
    val created_at: String,
    val description: String,
    val id: String,
    val name: String,
    val products: List<Product>?,
    val provider: Provider,
    val provider_id: Int,
    var sizes: List<Size>,
    val status: String,
    val updated_at: String
) : Parcelable {

    companion object {

        val empty =
            PlanDetails(emptyList(), "", "", "", "", emptyList(), Provider.empty, -1, emptyList(), "", "")
    }
}