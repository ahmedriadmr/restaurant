package com.doubleclick.domain.model.auth

data class Role(
    val guard_name: String,
    val id: Int,
    val name: String,
    val permissions: List<Permission>,
    val pivot: Pivot,
)