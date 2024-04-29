package com.doubleclick.rovleapp

import android.app.Application
import android.content.Context
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveInitializer
import app.rive.runtime.kotlin.core.Rive
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppInitializer.getInstance(applicationContext)
            .initializeComponent(RiveInitializer::class.java)
        Rive.init(applicationContext)
    }

    init {
        instance = this
    }

    companion object {
        private var instance: AndroidApplication? = null
        fun applicationContext(): Context? {
            return instance?.applicationContext
        }
    }
}