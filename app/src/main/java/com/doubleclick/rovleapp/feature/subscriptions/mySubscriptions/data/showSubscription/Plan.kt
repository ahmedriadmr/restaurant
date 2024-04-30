package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.showSubscription

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plan(
    val created_at: String,
    val description: String,
    val id: String,
    val name: String,
    val provider: Provider,
    val provider_id: String,
    val status: String,
    val updated_at: String
) : Parcelable {

    companion object {

        val empty = Plan("", "", "", "", Provider.empty, "", "", "")
    }
}