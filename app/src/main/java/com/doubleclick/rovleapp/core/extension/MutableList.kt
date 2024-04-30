package com.doubleclick.restaurant.core.extension

fun <T> MutableList<T>.moveItemToFirst(item: T): List<T> {
    val index = indexOf(item)
    if (index != -1) {
        removeAt(index)
        add(0, item)
    }
    return this
}