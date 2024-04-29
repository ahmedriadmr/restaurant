package com.doubleclick.rovleapp.utils

import android.content.Context
import android.provider.Settings

fun Context.mobileId(): String {
    return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
}