package com.doubleclick.rovleapp.feature.profile.data.points

data class PointsResponse(
    val `data`: List<PointsData>,
    val message: String,
    val status: String
)