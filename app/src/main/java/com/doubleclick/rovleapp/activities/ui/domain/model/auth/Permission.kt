package com.doubleclick.domain.model.auth

data class Permission(
    val created_at: String?,
    val guard_name: String?,
    val id: Int,
    val name: String?,
    val pivot: Pivot?,
    val updated_at: String?
)