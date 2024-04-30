package com.doubleclick.restaurant.feature.profile.data.rates

data class RatingResponse(
    val `data`: RatingData,
    val message: String,
    val status: String
)