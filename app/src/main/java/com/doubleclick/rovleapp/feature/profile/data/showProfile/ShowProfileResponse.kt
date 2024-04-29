package com.doubleclick.rovleapp.feature.profile.data.showProfile

data class ShowProfileResponse(
    val `data`: ProfileDetails,
    val message: String,
    val status: String
)