package com.doubleclick.rovleapp.core.extension

import android.content.Context
import android.net.ConnectivityManager
import androidx.datastore.dataStore
import com.doubleclick.rovleapp.core.platform.local.AppSettingsSerializer

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

val Context.dataStore by dataStore("rovle-app-settings.json", AppSettingsSerializer)
