package net.app.catsnetapp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.network.ApiResponse
import net.app.catsnetapp.network.CatsApiService
import net.app.catsnetapp.network.Success
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

class CatsNetRepository(private val apiService: CatsApiService) : KoinComponent {

    private val errorsHandler: ErrorsHandler by inject()

    suspend fun fetchCatsImages(): ApiResponse<List<Cat>?, String?> {
        return withContext(Dispatchers.IO) {
            try {
                Success(apiService.fetchCatsImages(10).body())
            } catch (e: Exception) {
                errorsHandler.handle(e)
            }
        }
    }
}