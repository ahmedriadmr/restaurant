package com.doubleclick.restaurant.feature.passport.logros.data.newResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class NewLogrosData(
    val tasks: List<Task>,
    val user_level_id: Int
): Parcelable {

    companion object {
        val empty = NewLogrosData(emptyList(),  -1)
    }
}