package com.example.fortnite_tracker

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FortniteApiService {
    @GET("v1/lookup")
    suspend fun getAccountId(
        @Query("nickname") nickname: String,
        @Header("Authorization") apiKey: String
    ): Response<AccountLookupResponse>

    @GET("v1/stats")
    suspend fun getPlayerStats(
        @Query("account") accountId: String,
        @Header("Authorization") apiKey: String
    ): Response<PlayerStatsResponse>
}
