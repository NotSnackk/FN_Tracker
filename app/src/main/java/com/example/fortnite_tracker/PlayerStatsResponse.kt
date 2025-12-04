package com.example.fortnite_tracker.models

import com.google.gson.annotations.SerializedName

// Model dla odpowiedzi wyszukiwania gracza (lookup)
data class AccountLookupResponse(
    val account_id: String,
    val name: String
)

// Model dla odpowiedzi statystyk gracza (PlayerStatsResponse) [cite: 43]
data class PlayerStatsResponse(
    val account_id: String,
val name: String,
@SerializedName("global_stats")
val globalStats: GlobalStats
)

// Model dla statystyk globalnych [cite: 48]
data class GlobalStats(
    val solo: ModeStats?,
val duo: ModeStats?,
val squad: ModeStats?
)

// Model dla statystyk pojedynczego trybu (np. Solo) [cite: 53]
data class ModeStats(
    @SerializedName("matchesplayed")
    val matchesPlayed: Int,
val wins: Int,
val kills: Int
)
