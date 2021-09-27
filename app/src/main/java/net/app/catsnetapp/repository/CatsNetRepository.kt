package net.app.catsnetapp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.app.catsnetapp.network.CatsApiService

class CatsNetRepository(private val apiService: CatsApiService) {

    suspend fun callTest() {
        withContext(Dispatchers.IO) {
            try {
                apiService.testCall(1)

            } catch (e: Exception) {

            }
        }
    }
}