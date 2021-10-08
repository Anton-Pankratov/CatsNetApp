package net.app.catsnetapp.ui.cat

import android.content.ContentResolver
import com.bumptech.glide.RequestManager
import net.app.catsnetapp.models.StoredCatImage
import net.app.catsnetapp.repository.CatsNetRepository
import net.app.catsnetapp.ui.base.BaseViewModel

class CatViewModel(private val repository: CatsNetRepository) : BaseViewModel() {

    fun saveCatImage(contentResolver: ContentResolver?, image: StoredCatImage) {
        repository.saveCatImageInGallery(contentResolver, image)
    }
}