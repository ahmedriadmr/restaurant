package com.doubleclick.domain.model.carts.get

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val category_id: Int,
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val vip: String
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}