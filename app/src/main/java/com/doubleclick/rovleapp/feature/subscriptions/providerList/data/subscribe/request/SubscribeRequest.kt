package com.doubleclick.rovleapp.feature.subscriptions.providerList.data.subscribe.request

data class SubscribeRequest(
    val periodicity: String,
    val plan_id: String,
    val size_id: Int,
    val delivery_type: String,
    val zip_code: String,
    val name: String,
    val phone: String,
    val email: String,
    val address: String,
    val notes: String,
    val coffee_shop_id: String,
    val locker_location: String
)
