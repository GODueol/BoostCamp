package com.android.godueol.boostcamp.utlis

import android.content.Context
import android.util.DisplayMetrics
fun Double?.DpToPixel(context: Context): Double {
    this?: return 0.0
    val resources = context.resources
    val metrics = resources.displayMetrics
    return (this * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
}
