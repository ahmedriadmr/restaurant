package com.doubleclick.restaurant.feature.auth.login.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val address: String?,
    val card_id: String?,
    val city_id: String?,
    val country_id: String?,
    val created_at: String,
    val email: String,
    val email_verified_at: String?,
    val has_dashboard_access: Boolean,
    val id: String,
    val image: String?,
    val level_id: String,
    val name: String,
    val phone: String?,
    val province_id: String?,
    val roles: List<Role>,
    val updated_at: String,
    val zip: String?,
    val mango_id: String?,
    val wallet_id: String?
) : Parcelable {
    companion object {
        val empty = User(
            "",
            "",
            "",
            "",
            "",
            "", "", false, "", "", "", "", "", "", emptyList(), "", "", "", ""
        )
    }
}