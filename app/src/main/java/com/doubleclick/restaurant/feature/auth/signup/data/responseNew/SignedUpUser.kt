package com.doubleclick.restaurant.feature.auth.signup.data.responseNew

data class SignedUpUser(
    val address: String,
    val created_at: String,
    val email: String,
    val fcm_token: String,
    val frist_name: String,
    val id: String,
    val last_name: String,
    val phone: String,
    val updated_at: String
)