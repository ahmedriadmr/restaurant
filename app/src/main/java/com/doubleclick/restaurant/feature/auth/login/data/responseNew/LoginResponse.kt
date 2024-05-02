package com.doubleclick.restaurant.feature.auth.login.data.responseNew

data class LoginResponse(
    val user: NewUser,
    val permission: List<PermissionX>,
    val role: String,
    val token: String
)