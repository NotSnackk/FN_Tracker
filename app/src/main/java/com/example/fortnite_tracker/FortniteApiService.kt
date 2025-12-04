package com.example.fortnite_tracker.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import com.example.fortnite_tracker.models.AccountLookupResponse
import com.example.fortnite_tracker.models.PlayerStatsResponse

interface FortniteApiService {

    // Wyszukanie Account ID na podstawie nicku gracza [cite: 22, 23, 24, 25, 26]
    @GET("v1/lookup")
    suspend fun getAccountId(
        @Query("nickname") nickname: String,
        @Header("Authorization") apiKey: String
    ): Response<AccountLookupResponse>

    // Pobranie statystyk gracza na podstawie Account ID [cite: 27, 28, 29, 30, 31]
    @GET("v1/stats")
    suspend fun getPlayerStats(
        @Query("account") accountId: String,
        @Header("Authorization") apiKey: String
    ): Response<PlayerStatsResponse>
}
