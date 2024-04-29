package com.doubleclick.rovleapp.feature.auth.signup.data.response

data class NewRes(
    val `data`: UserData,
    val message: String,
    val status: Int
)