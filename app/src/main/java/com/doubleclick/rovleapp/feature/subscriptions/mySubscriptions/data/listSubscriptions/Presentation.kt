package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.listSubscriptions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Presentation(
    val created_at: String,
    val id: String,
    val pivot: Pivot?,
    val price: Int,
    val product_id: Int,
    val provider_id: Int,
    val units: Int,
    val updated_at: String,
    val weight: Int
) : Parcelable