package com.doubleclick.restaurant.feature.auth.signup.data.response

data class ErrorsSignUp(
    val user_email: List<String>,
    val user_password: List<String>
)