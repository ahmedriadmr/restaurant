package com.doubleclick.rovleapp.feature.shop.cartDetails.data.filterCities

data class FilterCitiesResponse(
    val `data`: List<FilterCitiesData>,
    val message: String,
    val status: Int
)