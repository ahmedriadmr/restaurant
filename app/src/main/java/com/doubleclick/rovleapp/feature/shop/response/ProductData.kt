package com.doubleclick.restaurant.feature.shop.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductData(
    val current_page: Int,
    val `data`: List<Product>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
    val last_page_url: String,
    val links: List<Link>,
    val next_page_url: String?,
    val path: String,
    val per_page: Int,
    val prev_page_url: String?,
    val to: Int,
    val total: Int
) : Parcelable {

    companion object {
        val empty = ProductData(
            -1, emptyList(), "", -1, -1, "", emptyList(), "", "", -1, "",
            -1, -1
        )
    }
}