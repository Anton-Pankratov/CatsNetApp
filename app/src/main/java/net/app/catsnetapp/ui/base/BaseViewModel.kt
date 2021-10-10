package net.app.catsnetapp.ui.base

import androidx.lifecycle.ViewModel
import com.bumptech.glide.RequestManager
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.models.StoredCatImage
import net.app.catsnetapp.utils.DI_GLIDE
import net.app.catsnetapp.utils.getCatImage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val glide: RequestManager by inject(named(DI_GLIDE))

    val keptCat get() = selectedCat

    suspend fun formStoredCat(): StoredCatImage {
        return StoredCatImage(
            bitmap = getCatImage(keptCat?.url, glide),
            name = keptCat?.getName(),
            url = keptCat?.url,
            ext = keptCat?.ext
        )
    }

    fun keepSelectedCat(cat: Cat?) {
        selectedCat = cat
    }

    companion object {
        var selectedCat: Cat? = null
    }
}