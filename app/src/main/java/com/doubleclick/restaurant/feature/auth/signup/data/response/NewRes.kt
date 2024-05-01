package com.doubleclick.restaurant.feature.auth.signup.data.response

data class NewRes(
    val `data`: UserData,
    val message: String,
    val status: Int
)