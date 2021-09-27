package net.app.catsnetapp.network

import net.app.catsnetapp.utils.API_KEY
import net.app.catsnetapp.utils.CONNECT_TIME
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class CatsOkHttpClient(private val api_key: String) {

    private val apiInterceptor = Interceptor { chain ->
        with(chain) {
            proceed(
                request().newBuilder()
                    .addHeader(API_KEY, api_key)
                    .build()
            )
        }
    }

    private val loggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    fun build(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(apiInterceptor)
            addNetworkInterceptor(loggingInterceptor)
            connectTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS)
            readTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS)
            writeTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS)
        }.build()
    }
}