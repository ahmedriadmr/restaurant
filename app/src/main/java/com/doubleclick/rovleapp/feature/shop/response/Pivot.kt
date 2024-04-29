package com.doubleclick.rovleapp.feature.shop.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pivot(
    val coffee_shop_id: Int,
    val product_id: Int,
    val origin_id: Int
) : Parcelable{

    companion object {
        val empty = Pivot(
            -1,
            -1,
            -1
        )
    }
}