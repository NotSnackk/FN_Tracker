package com.example.fortnite_tracker

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.fortnite_tracker.databinding.SearchActivityBinding
import com.example.fortnite_tracker.databinding.StartActivityBinding

class SearchActivity : AppCompatActivity(){
    private lateinit var binding: SearchActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchview = binding.searchview

        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    // Przejście do MainActivity z przekazaniem tekstu
                    val intent = Intent(this@SearchActivity, MainActivity::class.java)
                    intent.putExtra("SEARCH_QUERY", query)
                    startActivity(intent)
                    finish() // opcjonalnie zamykamy SearchActivity
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Możesz tu obsłużyć zmiany tekstu jeśli chcesz
                return false
            }
        })

    }
}