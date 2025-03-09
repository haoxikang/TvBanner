package com.peipei.tvbanner.ui.view

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.time.Duration

fun isNearFraction(x: Float, threshold: Float): Boolean {
    if (abs(x)>0.5)return false
    val xDouble = x.toDouble()
    val scaled = xDouble * 10.0
    val n = scaled.roundToInt()
    val nearest = n / 10.0
    val difference = abs(xDouble - nearest)
    return difference <= threshold.toDouble()
}


fun <T> Flow<T>.throttleFirst(windowDuration: Duration): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { value ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime >= windowDuration.inWholeMilliseconds) {
            emit(value)
            lastEmissionTime = currentTime
        }
    }
}


fun getVibrator(context: Context): Vibrator {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
}

fun vibrate(vibrator: Vibrator) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(50L, VibrationEffect.EFFECT_HEAVY_CLICK))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(50L)
    }
}