package com.doubleclick.rovleapp.feature.auth.login.data.response

data class LoginRes<T>(
    val message: String,
    val status: Int,
    val token: String,
    val errors: ErrorsLogin? = ErrorsLogin(emptyList()),
    val user: User
)