package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.showSubscription

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Size(
    val created_at: String,
    val id: Int,
    val name: String,
    val provider_id: Int,
    val updated_at: String,
    val weight: String
) : Parcelable {

    companion object {

        val empty = Size("", -1, "", -1, "", "")
    }
}