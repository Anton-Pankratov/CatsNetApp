package net.app.catsnetapp.data.storage

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow
import net.app.catsnetapp.models.StoredCatImage
import java.io.OutputStream

interface CatImageSaver {

    val saveImageState: LiveData<SaveImageState>

    fun saveCatImageInGallery(contentResolver: ContentResolver?, storedImage: StoredCatImage)

    fun StoredCatImage.saveForApi29AndMore(contentResolver: ContentResolver?): OutputStream?

    fun StoredCatImage.saveForApi29Less(): OutputStream?
}