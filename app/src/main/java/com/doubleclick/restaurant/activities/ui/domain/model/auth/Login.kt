package com.doubleclick.domain.model.auth

data class Login(
    val email: String,
    val password: String,
    val fcm_token: String,
)
