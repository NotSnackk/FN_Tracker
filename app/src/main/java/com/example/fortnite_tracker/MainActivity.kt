package com.example.fortnite_tracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fortnite_tracker.databinding.MainActivityBinding
import com.example.fortnite_tracker.viewmodel.StatsViewModel
import com.example.fortnite_tracker.models.AccountLookupResponse
import com.example.fortnite_tracker.models.PlayerStatsResponse
import com.example.fortnite_tracker.models.GlobalStats
import com.example.fortnite_tracker.models.ModeStats
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.fortnite_tracker.api.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var statsViewModel: StatsViewModel
    private lateinit var binding: MainActivityBinding
    // Symulacja obiektu binding do elementów UI
    // private val binding: ActivityMainBinding...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nickname = intent.getStringExtra("SEARCH_QUERY")
        if (!nickname.isNullOrEmpty()) {
            loadStats(nickname)
        } else {
            Toast.makeText(this, "Brak nicku gracza", Toast.LENGTH_SHORT).show()
        }
    }
    private fun loadStats(nickname: String) {
        lifecycleScope.launch(Dispatchers.IO){
            try {
                var stats: PlayerStatsResponse?
                // 1. Pobranie Account ID
                val accountResponse = ApiClient.retrofitService.getAccountId(nickname, "48778d4c-79e943f8-4e31f1cc-9bd21d12")

                if (accountResponse.isSuccessful) {
                    val accountId = accountResponse.body()?.account_id

                    if (accountId != null) {
                        // 2. Pobranie statystyk gracza
                        val statsResponse  = ApiClient.retrofitService.getPlayerStats(accountId, "48778d4c-79e943f8-4e31f1cc-9bd21d12")
                        if (statsResponse.isSuccessful) {
                            stats = statsResponse.body()
                            displayStats(stats)
                        } else {
                            binding.error.text ="Nie udało się pobrać statystyk gracza."
                        }
                    } else {
                        binding.error.text = "Nie znaleziono gracza o podanym nicku."
                    }
                } else {
                    binding.error.text = ("Błąd komunikacji z API.")
                }
            } catch (e: Exception) {
                binding.error.text = "Błąd komunikacji z API: ${e.message}"
            }
        }



    }
    private fun displayStats(stats: PlayerStatsResponse?) {
        if (stats!=null){
            binding.nickname.text = stats.name
            binding.kills.text = stats.globalStats.solo?.kills.toString()
            binding.wins.text = stats.globalStats.solo?.wins.toString()
            binding.matches.text = stats.globalStats.solo?.matchesPlayed.toString()
        }else{
            binding.error.text = "Nie udalo sie wyswietlic statystyk"
        }
    }

}