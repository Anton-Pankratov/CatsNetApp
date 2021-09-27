package net.app.catsnetapp.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CatsRetrofitService(
    private val base_url: String,
    private val moshi: Moshi,
    private val okHttpClient: OkHttpClient
) {

    fun build(): CatsApiService {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory
                    .create(moshi)
            )
            .build()
            .create(CatsApiService::class.java)
    }
}