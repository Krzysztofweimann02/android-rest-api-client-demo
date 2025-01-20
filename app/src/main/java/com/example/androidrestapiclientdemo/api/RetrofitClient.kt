package com.example.androidrestapiclientdemo.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://192.168.56.1:8080"

    val apiService: ApiService by lazy {
        val gson = GsonBuilder()
            .setLenient() // Obsługa niepoprawnego JSON-a
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}