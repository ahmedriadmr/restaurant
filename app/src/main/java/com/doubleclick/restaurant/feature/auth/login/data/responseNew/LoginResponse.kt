package com.doubleclick.restaurant.feature.auth.login.data.responseNew

data class LoginResponse<T>(
    val user: NewUser,
    val permission: List<PermissionX>,
    val role: String,
    val token: String
)