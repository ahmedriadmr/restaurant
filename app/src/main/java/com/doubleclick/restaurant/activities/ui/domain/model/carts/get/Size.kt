package com.doubleclick.domain.model.carts.get

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Size(
    val id: Int,
    val item: Item,
    val item_id: Int,
    val name: String,
    val price: Double,
    val status: String
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Size

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}