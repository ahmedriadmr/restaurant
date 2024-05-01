package com.doubleclick.domain.model.auth

data class ResetPassword(
    val email: String,
    val password: String,
    val password_confirmation: String
)
