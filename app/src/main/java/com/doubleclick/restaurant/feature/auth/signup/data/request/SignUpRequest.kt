package com.doubleclick.restaurant.feature.auth.signup.data.request

data class SignUpRequest(
    val frist_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val password_confirmation: String,
    val phone: String,
    val address: String,
    val fcm_token: String
)