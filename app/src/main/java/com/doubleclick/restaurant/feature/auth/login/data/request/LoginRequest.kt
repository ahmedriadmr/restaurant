package com.doubleclick.restaurant.feature.auth.login.data.request

data class LoginRequest(
    val email: String, val password: String,
    val fcm_token: String
)