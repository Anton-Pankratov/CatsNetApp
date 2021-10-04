package net.app.catsnetapp.data.storage

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.app.catsnetapp.models.StoredCatImage
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import android.provider.MediaStore.MediaColumns as Column

class CatImageSaverImpl : CatImageSaver {

    private val _saveImageState = MutableStateFlow(SaveImageState.NONE)

    override val saveImageState: StateFlow<SaveImageState>
        get() = _saveImageState

    override fun saveCatImageInGallery(
        contentResolver: ContentResolver?,
        storedImage: StoredCatImage
    ) {
        storedImage.apply {
            var stream: OutputStream? = null
            try {
                stream = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    saveForApi29AndMore(contentResolver)
                } else {
                    saveForApi29Less()
                }
                stream.use {
                    bitmap?.compress(
                        Bitmap.CompressFormat.JPEG, 100, it
                    )
                    _saveImageState.tryEmit(SaveImageState.SUCCESS)
                }
            } catch (e: Exception) {
                _saveImageState.tryEmit(SaveImageState.FAILURE)
            } finally {
                stream?.close()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun StoredCatImage.saveForApi29AndMore(
        contentResolver: ContentResolver?
    ): OutputStream? {
        return contentResolver?.let {
            it.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues().apply {
                    put(Column.DISPLAY_NAME, "$name$ext")
                    put(Column.MIME_TYPE, "image/$ext")
                    put(Column.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
            )?.let { uri ->
                it.openOutputStream(uri)
            }
        }
    }

    override fun StoredCatImage.saveForApi29Less(): OutputStream? {
        return Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        ).let { file ->
            FileOutputStream(File(file, "$name$ext"))
        }
    }
}