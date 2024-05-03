package com.doubleclick.restaurant.feature.auth.login.data.responseNew

import com.doubleclick.restaurant.feature.auth.signup.data.response.UserData

data class LoginResponse<T>(
    val permission: List<Permission>,
    val role: String,
    val token: String,
    val user: UserData
)