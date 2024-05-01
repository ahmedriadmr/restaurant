package com.doubleclick.domain.model.category.get

data class Category(
    val id: Int,
    val image: String,
    val items: List<Item>,
    val items_count: Int,
    val name: String,
    val status: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}