package com.doubleclick.restaurant.feature.auth.signup.data.responseNew

data class SignUpResponse<T>(
    val role: String,
    val user: SignedUpUser
)