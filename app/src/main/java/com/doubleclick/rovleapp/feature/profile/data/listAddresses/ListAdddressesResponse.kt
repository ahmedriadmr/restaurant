package com.doubleclick.restaurant.feature.profile.data.listAddresses

data class ListAdddressesResponse(
    val `data`: List<AddressesData>,
    val message: String,
    val status: Int
)