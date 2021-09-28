package net.app.catsnetapp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.models.requests.ImagesRequest
import net.app.catsnetapp.network.ApiResponse
import net.app.catsnetapp.network.CatsApiService
import net.app.catsnetapp.network.Failure
import net.app.catsnetapp.network.Success
import net.app.catsnetapp.utils.DEFAULT_ERROR_MESSAGE

class CatsNetRepository(private val apiService: CatsApiService) {

    suspend fun fetchCatsImages(request: ImagesRequest): ApiResponse<List<Cat>, String> {
        return withContext(Dispatchers.IO) {
            try {
                Success(apiService.fetchCatsImages(request))
            } catch (e: Exception) {
                Failure(e.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }
}