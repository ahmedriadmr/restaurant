package com.doubleclick.restaurant.feature.auth.signup.data.responseNew

data class SignUpResponse(
    val `data`: SignedUpUser,
    val token: String
)