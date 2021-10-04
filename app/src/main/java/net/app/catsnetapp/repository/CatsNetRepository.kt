package net.app.catsnetapp.repository

import android.content.ContentResolver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import net.app.catsnetapp.data.network.ApiResponse
import net.app.catsnetapp.data.network.CatsApiService
import net.app.catsnetapp.data.network.Success
import net.app.catsnetapp.data.storage.CatImageSaver
import net.app.catsnetapp.data.storage.SaveImageState
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.models.StoredCatImage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CatsNetRepository(
    private val apiService: CatsApiService,
    private val inGallerySaver: CatImageSaver
) : KoinComponent {

    val saveState: StateFlow<SaveImageState>
        get() = inGallerySaver.saveImageState

    private val errorsHandler: ErrorsHandler by inject()

    suspend fun fetchCatsImages(): ApiResponse<List<Cat>?, String?> {
        return withContext(Dispatchers.IO) {
            try {
                Success(apiService.fetchCatsImages("full", 10).body())
            } catch (e: Exception) {
                errorsHandler.handle(e)
            }
        }
    }

    fun saveCatImageInGallery(contentResolver: ContentResolver?, image: StoredCatImage) {
        inGallerySaver.saveCatImageInGallery(contentResolver, image)
    }
}