package com.doubleclick.domain.model.carts.get

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartsModel(
    val id: Int,
    var number: Int,
    val size: Size,
    val size_id: Int,
    val user_id: Int
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CartsModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}