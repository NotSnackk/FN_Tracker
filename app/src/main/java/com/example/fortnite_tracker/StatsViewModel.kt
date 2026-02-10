package com.example.fortnite_tracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

enum class GameMode {
    SOLO,
    DUO,
    SQUAD
}

data class StatsUiState(
    val nickname: String = "",
    val selectedMode: GameMode = GameMode.SOLO,
    val kills: String = "-",
    val wins: String = "-",
    val matches: String = "-",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)

class StatsViewModel : ViewModel() {

    private val apiKey = BuildConfig.FORTNITE_API_KEY

    private val _uiState = MutableLiveData(StatsUiState())
    val uiState: LiveData<StatsUiState> = _uiState

    private var cachedStats: PlayerStatsResponse? = null

    fun loadStats(nickname: String) {
        val trimmedNickname = nickname.trim()
        if (trimmedNickname.isEmpty()) {
            _uiState.value = _uiState.value?.copy(errorMessage = "Podaj nick gracza.")
            return
        }

        _uiState.value = _uiState.value?.copy(
            nickname = trimmedNickname,
            isLoading = true,
            errorMessage = ""
        )

        if (apiKey.isBlank()) {
            setError("Brak klucza API. Dodaj FORTNITE_API_KEY do local.properties.")
            return
        }

        viewModelScope.launch {
            try {
                val accountResponse = ApiClient.retrofitService.getAccountId(trimmedNickname, apiKey)

                if (!accountResponse.isSuccessful) {
                    setError("Błąd komunikacji z API (lookup).")
                    return@launch
                }

                val accountId = accountResponse.body()?.account_id
                if (accountId.isNullOrBlank()) {
                    setError("Nie znaleziono gracza o podanym nicku.")
                    return@launch
                }

                val statsResponse = ApiClient.retrofitService.getPlayerStats(accountId, apiKey)
                if (!statsResponse.isSuccessful) {
                    setError("Nie udało się pobrać statystyk gracza.")
                    return@launch
                }

                cachedStats = statsResponse.body()
                applyMode(_uiState.value?.selectedMode ?: GameMode.SOLO)
            } catch (exception: Exception) {
                setError("Błąd połączenia: ${exception.message}")
            }
        }
    }

    fun onModeSelected(mode: GameMode) {
        applyMode(mode)
    }

    private fun applyMode(mode: GameMode) {
        val stats = cachedStats
        if (stats == null) {
            _uiState.value = _uiState.value?.copy(
                selectedMode = mode,
                isLoading = false
            )
            return
        }

        val modeStats = when (mode) {
            GameMode.SOLO -> stats.globalStats?.solo
            GameMode.DUO -> stats.globalStats?.duo
            GameMode.SQUAD -> stats.globalStats?.squad
        }

        _uiState.value = StatsUiState(
            nickname = stats.name,
            selectedMode = mode,
            kills = modeStats?.kills?.toString() ?: "-",
            wins = modeStats?.wins?.toString() ?: "-",
            matches = modeStats?.matchesPlayed?.toString() ?: "-",
            isLoading = false,
            errorMessage = if (modeStats == null) "Brak danych dla trybu ${mode.name}." else ""
        )
    }

    private fun setError(message: String) {
        _uiState.value = _uiState.value?.copy(
            isLoading = false,
            errorMessage = message,
            kills = "-",
            wins = "-",
            matches = "-"
        )
    }
}
