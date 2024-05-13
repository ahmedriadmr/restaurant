package com.doubleclick.restaurant.feature.admin.data.addStaff.request

data class AddStaffRequest(
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: String,
    val address: String,
    val password: String,
    val password_confirmation: String,
    val fcm_token: String,
    val user_role: String,
)
