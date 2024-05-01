package com.doubleclick.restaurant.core.extension

import android.content.Context
import android.net.ConnectivityManager
import androidx.datastore.dataStore
import com.doubleclick.restaurant.core.platform.local.AppSettingsSerializer

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

val Context.dataStore by dataStore("restaurant-app-settings.json", AppSettingsSerializer)
