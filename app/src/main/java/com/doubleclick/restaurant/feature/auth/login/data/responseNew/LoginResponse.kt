package com.doubleclick.restaurant.feature.auth.login.data.responseNew

data class LoginResponse<T>(
    val permission: List<Permission>,
    val role: String,
    val token: String,
    val user: NewUser
)