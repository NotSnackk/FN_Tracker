package com.example.fortnite_tracker

import com.google.gson.annotations.SerializedName

data class AccountLookupResponse(
    val account_id: String,
    val name: String
)

data class PlayerStatsResponse(
    val account_id: String,
    val name: String,
    @SerializedName("global_stats")
    val globalStats: GlobalStats?
)

data class GlobalStats(
    val solo: ModeStats?,
    val duo: ModeStats?,
    val squad: ModeStats?
)

data class ModeStats(
    @SerializedName("matchesplayed")
    val matchesPlayed: Int?,
    val wins: Int?,
    val kills: Int?
)
