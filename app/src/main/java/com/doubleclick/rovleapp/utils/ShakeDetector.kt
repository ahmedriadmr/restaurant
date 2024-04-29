package com.doubleclick.rovleapp.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector(private val onShakeListener: (x: Float, y: Float) -> Unit) :
    SensorEventListener {

    private val shakeThreshold = 5
    private val shakeTimeInterval = 1000

    private var lastShakeTime: Long = 0

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val acceleration =
            sqrt((x * x + y * y + z * z).toDouble()) - SensorManager.GRAVITY_EARTH

        if (acceleration > shakeThreshold) {
            val currentTime = System.currentTimeMillis()

            if (currentTime - lastShakeTime > shakeTimeInterval) {
                lastShakeTime = currentTime
                onShakeListener.invoke(x, y)
            }
        }
    }
}