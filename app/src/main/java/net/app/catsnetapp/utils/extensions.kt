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
                hide(
                    WindowInsets.Type.statusBars()
                            or WindowInsets.Type.navigationBars()
                )
                systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
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
                load(url).apply(createGlideRequestOptions()).into(this@run)
            } else {
                asGif().load(url).apply(createGlideRequestOptions()).into(this@run)
            }
        }
    }
}

fun ImageView.setDownloadIcon(
    url: String?,
    glide: RequestManager
) {
    CoroutineScope(Dispatchers.IO).launch {
        glide.apply {
            (asBitmap()
                .load(url)
                .apply(createGlideRequestOptions())
                .submit()
                .get()).apply {
                    setIconByCheck(isNearBlack())
                }
        }
    }

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

/* android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
        at android.view.ViewRootImpl.checkThread(ViewRootImpl.java:7753)
        at android.view.ViewRootImpl.requestLayout(ViewRootImpl.java:1225)
        at android.view.View.requestLayout(View.java:23093)
        at android.view.View.requestLayout(View.java:23093)
        at android.view.View.requestLayout(View.java:23093)
        at android.view.View.requestLayout(View.java:23093)
        at android.view.View.requestLayout(View.java:23093)
        at android.view.View.requestLayout(View.java:23093)
        at android.view.View.requestLayout(View.java:23093)
        at android.view.View.requestLayout(View.java:23093)
        at android.view.View.requestLayout(View.java:23093)
        at androidx.constraintlayout.widget.ConstraintLayout.requestLayout(ConstraintLayout.java:3146)
        at android.view.View.requestLayout(View.java:23093)
        at android.widget.ImageView.setImageDrawable(ImageView.java:571)
        at androidx.appcompat.widget.AppCompatImageView.setImageDrawable(AppCompatImageView.java:104)
        at android.widget.ImageView.setImageBitmap(ImageView.java:705)
        at androidx.appcompat.widget.AppCompatImageView.setImageBitmap(AppCompatImageView.java:112)
        at net.app.catsnetapp.utils.ExtensionsKt.setIconByCheck(extensions.kt:134)
        at net.app.catsnetapp.utils.ExtensionsKt$setDownloadIcon$1.invokeSuspend(extensions.kt:126)
        at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
        at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:106)
        at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:571)
        at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.executeTask(CoroutineScheduler.kt:750)
        at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.runWorker(CoroutineScheduler.kt:678)
        at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:665)*/

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