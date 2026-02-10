package com.example.fortnite_tracker

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fortnite_tracker.databinding.SearchActivityBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: SearchActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val nickname = query?.trim().orEmpty()
                if (nickname.isBlank()) {
                    Toast.makeText(this@SearchActivity, "Wpisz nick gracza", Toast.LENGTH_SHORT).show()
                    return true
                }

                val intent = Intent(this@SearchActivity, MainActivity::class.java)
                intent.putExtra("SEARCH_QUERY", nickname)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }
}
