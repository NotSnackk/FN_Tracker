package com.example.fortnite_tracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fortnite_tracker.api.ApiClient
import com.example.fortnite_tracker.models.PlayerStatsResponse
import kotlinx.coroutines.launch

class StatsViewModel : ViewModel() {

    private val API_KEY = "48778d4c-79e943f8-4e31f1cc-9bd21d12" // Użyj swojego klucza API

    private val _playerStatsLiveData = MutableLiveData<PlayerStatsResponse?>()
    val playerStatsLiveData: LiveData<PlayerStatsResponse?> = _playerStatsLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    // Funkcja pobierająca statystyki [cite: 58]
    fun loadStats (nickname: String) {
        viewModelScope.launch {
            try {
                // 1. Pobranie Account ID
                val accountResponse = ApiClient.retrofitService.getAccountId(nickname, API_KEY)

                if (accountResponse.isSuccessful) {
                    val accountId = accountResponse.body()?.account_id

                    if (accountId != null) {
                        // 2. Pobranie statystyk gracza
                        val statsResponse  = ApiClient.retrofitService.getPlayerStats(accountId, API_KEY)
                        if (statsResponse.isSuccessful) {
                            val stats = statsResponse.body()
                            _playerStatsLiveData.postValue(stats)
                        } else {
                            _errorLiveData.postValue("Nie udało się pobrać statystyk gracza.")
                        }
                    } else {
                        _errorLiveData.postValue("Nie znaleziono gracza o podanym nicku.")
                    }
                } else {
                    _errorLiveData.postValue("Błąd komunikacji z API.")
                }
            } catch (e: Exception) {
                _errorLiveData.postValue("Błąd komunikacji z API: ${e.message}")
            }
        }
    }
}