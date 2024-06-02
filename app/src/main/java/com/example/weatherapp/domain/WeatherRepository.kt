package com.example.weatherapp.domain

interface WeatherRepository {

    suspend fun getWeatherFiveDays(
        city: String,
        onSuccess: (List<WeatherInfo>) -> Unit,
        onError: () -> Unit,
        onServerError: () -> Unit
    )
}