package com.doubleclick.restaurant.feature.auth.signup.data.responseNew

data class SIgnUpResponse(
    val `data`: SignedUpUser,
    val token: String
)