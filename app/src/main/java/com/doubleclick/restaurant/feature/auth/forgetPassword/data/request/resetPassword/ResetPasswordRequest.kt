package com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.resetPassword

data class ResetPasswordRequest(val email: String, val password: String, val password_confirmation: String)
