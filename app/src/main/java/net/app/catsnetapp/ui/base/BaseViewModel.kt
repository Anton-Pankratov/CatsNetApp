package net.app.catsnetapp.ui.base

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import net.app.catsnetapp.utils.DI_COIL_IMAGE_LOADER
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val imageLoader: ImageLoader by inject(named(DI_COIL_IMAGE_LOADER))

    val keptCatImage get() = selectedCatImage

    fun keepCatDrawable(image: String?) {
        selectedCatImage = image
    }

    companion object {
        var selectedCatImage: String? = null
    }
}