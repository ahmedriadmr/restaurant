package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.listSubscriptions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanSize(
    val created_at: String,
    val id: String,
    val plan_id: String,
    val price: String,
    val size: Size,
    val size_id: Int,
    val status: String,
    val updated_at: String
) : Parcelable