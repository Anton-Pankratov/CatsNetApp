package net.app.catsnetapp.utils

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import kotlin.math.roundToInt
import android.animation.PropertyValuesHolder as PropHolder

@SuppressLint("InlinedApi")
fun Activity.configureSystemBars() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.setDecorFitsSystemWindows(false)
        if (window.insetsController != null) {
            window.insetsController!!.hide(
                WindowInsets.Type.statusBars()
                        or WindowInsets.Type.navigationBars()
            )
            window.insetsController!!.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}

fun View.startFlipAnimation() {
    ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropHolder.ofFloat("scaleX", 0.0f, 1.0f),
        PropHolder.ofFloat("scaleY", 0.0f, 1.0f),
        PropHolder.ofFloat("alpha", 0.0f, 1.0f),
        PropHolder.ofFloat("rotationY", 0f, 360f),
    ).apply {
        duration = 1000
    }.start()
}

fun Context.toDp(value: Int): Int {
    return (value * resources.displayMetrics.density + 0.5f).roundToInt()
}

fun ImageView.setCatImage(url: String?, imageLoader: ImageLoader) {
    imageLoader.enqueue(
        ImageRequest.Builder(context).apply {
            data(url)
            crossfade(true)
            transformations(RoundedCornersTransformation(CORNERS_SIZE))
            target(this@setCatImage)
        }.build()
    )
}
