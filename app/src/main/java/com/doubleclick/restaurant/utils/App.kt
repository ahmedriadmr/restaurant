package com.doubleclick.restaurant.utils

import android.app.Application

class App : Application() {

    init {
        instance = this
    }


    companion object {
        private var instance: App? = null

    }

}