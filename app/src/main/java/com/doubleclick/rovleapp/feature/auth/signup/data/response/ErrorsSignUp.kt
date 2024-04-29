package com.doubleclick.rovleapp.feature.auth.signup.data.response

data class ErrorsSignUp(
    val user_email: List<String>,
    val user_password: List<String>
)