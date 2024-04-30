package com.doubleclick.restaurant.feature.passport.offers.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Offers(
    val providers: List<Provider>,
    val total_points: String
) : Parcelable {

    companion object {

        val empty = Offers(emptyList(), "")
    }
}