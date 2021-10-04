package net.app.catsnetapp.utils

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import net.app.catsnetapp.R
import net.app.catsnetapp.models.Cat
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

fun View.startAlphaAnimation() {
    startAnimation(
        AlphaAnimation(0.0f, 1.0f).apply {
            duration = 1000
            startOffset = 1000
            fillAfter = true
        }
    )
}

fun Context.toDp(value: Int): Int {
    return (value * resources.displayMetrics.density + 0.5f).roundToInt()
}

fun Context.getCatImage(
    url: String?,
    imageLoader: ImageLoader
): Bitmap? {
    var catImage: Bitmap? = null
    imageLoader.enqueue(
        ImageRequest.Builder(this).apply {
            data(url)
            crossfade(true)
            transformations(RoundedCornersTransformation(CORNERS_SIZE))
            target { image ->
                catImage = (image as BitmapDrawable).bitmap
            }
        }.build()
    )
    return catImage
}

fun Context.toast(@StringRes message: Int) {
    Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
}

fun ImageView.setImage(url: String?, imageLoader: ImageLoader) {
    imageLoader.enqueue(
        ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .transformations(RoundedCornersTransformation(CORNERS_SIZE))
            .target(this@setImage)
            .build()
    )
}

fun ImageView.setDownloadIcon(
    url: String?,
    target: ImageView,
    imageLoader: ImageLoader
) {
    imageLoader.enqueue(
        ImageRequest.Builder(context).apply {
            data(url)
            crossfade(true)
            transformations(RoundedCornersTransformation(CORNERS_SIZE))
            target { bitmap ->
                (bitmap as BitmapDrawable).bitmap.apply {
                    target.setIconByCheck(isNearBlack())
                }
            }
        }.build()
    )
}

fun ImageView.setIconByCheck(isNearBlack: Boolean) {
    setImageBitmap(
        ContextCompat.getDrawable(
            context,
            if (isNearBlack)
                R.drawable.ic_download_light
            else
                R.drawable.ic_download_dark
        )?.toBitmap()
    )
}

fun Bitmap.isNearBlack(): Boolean {
    with(getPixel(width - 20, height - 20)) {
        listOf(
            Color.red(this),
            Color.green(this),
            Color.blue(this)
        ).apply {
            return any { it <= 215 }
        }
    }
}