package net.app.catsnetapp.network

import net.app.catsnetapp.models.Cat
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CatsApiService {

    @GET("images/search")
    suspend fun testCall(@Query("size") size: Int): List<Cat>
}