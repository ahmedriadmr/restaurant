package com.doubleclick.rovleapp.feature.subscriptions.providerList.data.showPlan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PivotX(
    val plan_id: Int,
    val product_id: Int
) : Parcelable