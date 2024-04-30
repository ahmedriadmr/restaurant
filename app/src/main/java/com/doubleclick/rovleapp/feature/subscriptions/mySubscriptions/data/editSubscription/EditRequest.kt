package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.editSubscription

data class EditRequest(
    val periodicity: String,
    val plan_id: String,
    val size_id: Int,
    val notes: String,
    val delivery_type: String,
    val zip_code: String,
    val name: String,
    val phone: String,
    val email: String,
    val shipping: String,
    val coffee_shop_id: String,
    val locker_location: String,
    val address: String
)
