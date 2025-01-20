package com.example.androidrestapiclientdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.androidrestapiclientdemo.api.RetrofitClient
import com.example.androidrestapiclientdemo.api.data.TestResponse

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}


@Composable
fun MyApp() {
    var apiResponse by remember { mutableStateOf("Czekam na odpowiedź...") }
    var isLoading by remember { mutableStateOf(false) }

    // Funkcja pobierająca dane z serwera
    fun fetchData() {
        isLoading = true
        RetrofitClient.apiService.getTest().enqueue(object : Callback<TestResponse> {
            override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    apiResponse = response.body()?.message ?: "Brak danych"
                } else {
                    apiResponse = "Błąd serwera: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                isLoading = false
                apiResponse = "Błąd połączenia: ${t.message}"
            }
        })
    }

    // Pierwsze pobranie danych
    LaunchedEffect(Unit) {
        fetchData()
    }

    // UI
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Hello API") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator() // Wskaźnik ładowania
                } else {
                    Text(text = apiResponse, style = MaterialTheme.typography.h5)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { fetchData() }) { // Przycisk odświeżania
                    Text("Odśwież dane")
                }
            }
        }
    }
}