package com.doubleclick.rovleapp.feature.auth.signup.data.response

import android.os.Parcelable
import com.doubleclick.rovleapp.feature.auth.login.data.response.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val `data`: User,
    val token: String
) : Parcelable