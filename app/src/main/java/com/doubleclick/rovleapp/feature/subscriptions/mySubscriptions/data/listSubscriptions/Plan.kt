package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.listSubscriptions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plan(
    val created_at: String,
    val description: String,
    val id: String,
    val name: String,
    val price_per_kilo:String,
    val provider: Provider,
    val provider_id: Int,
    val status: String,
    val updated_at: String
) : Parcelable