package com.doubleclick.domain.model.auth

data class Register(
    val frist_name: String,
    val last_name: String,
    val phone: String,
    val address: String,
    val zip: String,
    val fcm_token: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)