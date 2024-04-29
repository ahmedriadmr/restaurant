package com.doubleclick.rovleapp.feature.passport.logros.data.newResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class Task(
    val activation_amount: String?,
    val activation_method: String?,
    val created_at: String?,
    val description: String?,
    val duration: Int?,
    val end_date: String?,
    val id: String,
    var is_done: Int?,
    val level_id: Int,
    val name: String?,
    val period: Int?,
    val repetition: Int?,
    val start_date: String?,
    val updated_at: String?,
    val user_amount: String?,
    val user_done_date: String?,
    val user_repetition: String?
) : Parcelable