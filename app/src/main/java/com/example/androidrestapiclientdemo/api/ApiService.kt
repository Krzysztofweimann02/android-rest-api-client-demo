package com.example.androidrestapiclientdemo.api

import com.example.androidrestapiclientdemo.api.data.TestResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/test")
    fun getTest(): Call<TestResponse>
}