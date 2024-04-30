package com.doubleclick.restaurant.core.functional

sealed class SortingOption {
    object Rating : SortingOption()
    object Sizes : SortingOption()
    object PriceAsc : SortingOption()
    object PriceDes : SortingOption()
    object Periodicity : SortingOption()

    companion object {
        const val SORTING = "sorting"
    }

    // Function to serialize the sealed class into a String
    fun SortingOption.toStringValue(): String {
        return when (this) {
            is Rating -> "Rating"
            is Sizes -> "Sizes"
            is PriceAsc -> "PriceAsc"
            is PriceDes -> "PriceDes"
            is Periodicity -> "Periodicity"
        }
    }

    // Function to deserialize the String into the sealed class
    fun String.toSortingOption(): SortingOption {
        return when (this) {
            "Rating" -> Rating
            "Sizes" -> Sizes
            "PriceAsc" -> PriceAsc
            "PriceDes" -> PriceDes
            "Periodicity" -> Periodicity
            else -> Periodicity
        }
    }
}