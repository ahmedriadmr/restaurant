package com.doubleclick.restaurant.core.functional

import android.os.Build



inline fun <T> sdk33AndUp(onSdk33: () -> T): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        onSdk33()
    } else null
}