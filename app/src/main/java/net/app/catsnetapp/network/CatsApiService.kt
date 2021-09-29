package net.app.catsnetapp.network

import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.models.requests.ImagesRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface CatsApiService {

    @GET("images/search")
    suspend fun fetchCatsImages(@Query("limit") limit: Int): Response<List<Cat>>
}