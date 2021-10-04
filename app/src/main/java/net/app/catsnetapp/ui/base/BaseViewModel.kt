package net.app.catsnetapp.ui.base

import androidx.lifecycle.ViewModel
import coil.ImageLoader
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.utils.DI_COIL_IMAGE_LOADER
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val timeStamp get() = System.currentTimeMillis().toString()

    val imageLoader: ImageLoader by inject(named(DI_COIL_IMAGE_LOADER))

    val keptCat get() = selectedCat

    fun keepSelectedCat(cat: Cat?) {
        selectedCat = cat
    }

    companion object {
        var selectedCat: Cat? = null
    }
}