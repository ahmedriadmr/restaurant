package com.doubleclick.domain.model.auth


data class Data(
    val address: String?,
    val device_token: String?,
    val email: String?,
    val fcm_token: String?,
    val frist_name: String?,
    val id: Int,
    val last_name: String?,
    val phone: String?,
    val roles: List<Role>,
    val status: String?,
)