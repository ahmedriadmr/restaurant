package com.doubleclick.rovleapp.feature.auth.signup.data.request

data class SignUpRequest(
    val user_name: String,
    val user_email: String,
    val user_password: String,
    val user_password_confirmation: String
)