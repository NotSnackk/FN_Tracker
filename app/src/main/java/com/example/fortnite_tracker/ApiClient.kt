package com.example.fortnite_tracker.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://fortniteapi.io/"

    // Instancja serwisu API dostępna dla całej aplikacji
    val retrofitService: FortniteApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // [cite: 40]
            .build()
            .create(FortniteApiService::class.java)
    }
}
