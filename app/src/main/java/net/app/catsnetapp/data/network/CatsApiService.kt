package net.app.catsnetapp.data.network

import net.app.catsnetapp.models.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApiService {

    @GET("images/search")
    suspend fun fetchCatsImages(
        @Query("size") size: String,
        @Query("limit") limit: Int,
    ): Response<List<Cat>>
}