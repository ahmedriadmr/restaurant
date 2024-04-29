package com.doubleclick.rovleapp.feature.auth.forgetPassword.data.request.resetPassword

data class ResetPasswordRequest(val email: String, val otp: String, val password: String, val password_confirmation: String)
