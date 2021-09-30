package net.app.catsnetapp.utils

import android.content.Context
import kotlin.math.roundToInt

fun Context.toDp(value: Int): Int {
    return (value * resources.displayMetrics.density + 0.5f).roundToInt()
}