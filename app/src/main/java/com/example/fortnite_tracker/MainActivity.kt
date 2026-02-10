package com.example.fortnite_tracker

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fortnite_tracker.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var statsViewModel: StatsViewModel
    private lateinit var binding: MainActivityBinding
    private var currentQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statsViewModel = ViewModelProvider(this)[StatsViewModel::class.java]

        setupModeSelector()
        setupObservers()

        val nickname = intent.getStringExtra("SEARCH_QUERY")
        if (!nickname.isNullOrBlank()) {
            currentQuery = nickname
            statsViewModel.loadStats(currentQuery)
        } else {
            Toast.makeText(this, "Brak nicku gracza", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupModeSelector() {
        val modes = listOf("SOLO", "DUO", "SQUAD")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, modes)
        binding.modeSpinner.adapter = adapter

        binding.modeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> statsViewModel.onModeSelected(GameMode.SOLO)
                    1 -> statsViewModel.onModeSelected(GameMode.DUO)
                    else -> statsViewModel.onModeSelected(GameMode.SQUAD)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        binding.retryButton.setOnClickListener {
            statsViewModel.loadStats(currentQuery)
        }
    }

    private fun setupObservers() {
        statsViewModel.uiState.observe(this) { state ->
            binding.nickname.text = state.nickname
            binding.kills.text = state.kills
            binding.wins.text = state.wins
            binding.matches.text = state.matches
            binding.error.text = state.errorMessage
            binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            binding.retryButton.visibility = if (state.errorMessage.isNotBlank()) View.VISIBLE else View.GONE
        }
    }
}
