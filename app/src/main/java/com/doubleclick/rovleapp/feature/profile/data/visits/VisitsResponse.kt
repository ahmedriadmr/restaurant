package com.doubleclick.restaurant.feature.profile.data.visits

data class VisitsResponse(
    val `data`: List<VisitsData>,
    val message: String,
    val status: Int
)