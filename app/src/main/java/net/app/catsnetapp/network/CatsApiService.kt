package net.app.catsnetapp.network

import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.models.requests.ImagesRequest
import retrofit2.Call
import retrofit2.http.*

interface CatsApiService {

    @GET("images/search")
    suspend fun fetchCatsImages(@Body request: ImagesRequest): List<Cat>
}