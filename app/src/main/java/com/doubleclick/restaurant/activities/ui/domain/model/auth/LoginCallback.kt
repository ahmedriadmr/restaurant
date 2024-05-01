package com.doubleclick.domain.model.auth

data class LoginCallback(
    val `data`: Data,
    val permission: List<Permission>,
    val role: String,
    val token: String
)