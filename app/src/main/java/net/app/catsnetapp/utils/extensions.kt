package net.app.catsnetapp.utils

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
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
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.*
import net.app.catsnetapp.R
import kotlin.math.roundToInt
import android.animation.PropertyValuesHolder as PropHolder

@SuppressLint("InlinedApi")
fun Activity.configureSystemBars() {
    with(window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setDecorFitsSystemWindows(false)
            insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
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

fun View.startAlphaAnimation(duration: Long) =
    useAlphaAnimation(0.0f, 1.0f, duration)

fun View.startAlphaAnimationReverse(duration: Long) =
    useAlphaAnimation(1.0f, 0.0f, duration)

private fun View.useAlphaAnimation(start: Float, end: Float, time: Long) {
    startAnimation(
        AlphaAnimation(start, end).apply {
            duration = time
            startOffset = time
            fillAfter = true
        }
    )
}

fun Context.toDp(value: Int): Int {
    return (value * resources.displayMetrics.density + 0.5f).roundToInt()
}

fun Context.toast(@StringRes message: Int) {
    Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
}

fun Context.showPermissionRequestDialog(
    title: String,
    body: String,
    callback: () -> Unit
) {
    AlertDialog.Builder(this).also {
        it.setTitle(title)
        it.setMessage(body)
        it.setPositiveButton("Ok") { _, _ ->
            callback()
        }
    }.create().show()
}

fun Context.createProgressDialog(glide: RequestManager): ImageView {
    return ImageView(this@createProgressDialog).apply {
        scaleX = 0.5f
        scaleY = 0.5f
        glide
            .load("file:///android_asset/progress_cat.gif")
            .into(this)
    }
}

suspend fun getCatImage(
    url: String?,
    glide: RequestManager
): Bitmap? {
    return withContext(Dispatchers.IO) {
        glide.asBitmap().load(url).submit().get()
    }
}

fun ImageView.setImage(url: String?, ext: String?, glide: RequestManager) {
    run {
        glide.apply {
            if (ext != "gif") {
                load(url)
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_cat_placeholder))
                    .apply(createGlideRequestOptions())
                    .into(this@setImage)
            } else {
                asGif().load(url)
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_cat_placeholder))
                    .apply(createGlideRequestOptions())
                    .into(this@setImage)
            }
        }
    }
}

fun ImageView.setDownloadIcon(
    url: String?,
    glide: RequestManager
) {
    var tries = 0
    if (tries == 2) {
        context.toast(R.string.save_image_exception)
        return
    }
    CoroutineScope(Dispatchers.IO).launch {
        try {
            getImage(url, glide)
        } catch (e: Exception) {
            tries++
            setDownloadIcon(url, glide)
        }
    }
}

private fun ImageView.getImage(url: String?, glide: RequestManager) {
    glide.apply {
        (
            asBitmap()
                .load(url)
                .apply(createGlideRequestOptions())
                .transition(BitmapTransitionOptions.withCrossFade(ANIM_DURATION_CROSS_FADE))
                .submit()
                .get()
            ).apply {
            setIconByCheck(isNearBlack())
        }
    }
}

fun ImageView.setIconByCheck(isNearBlack: Boolean) {
    setImageBitmap(
        ContextCompat.getDrawable(
            context,
            if (isNearBlack) {
                R.drawable.ic_download_light
            } else {
                R.drawable.ic_download_dark
            }
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

fun createGlideRequestOptions(): RequestOptions {
    return RequestOptions().transform(CenterCrop(), RoundedCorners(16))
}
