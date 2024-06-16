package com.example.weatherapp.domain

import com.example.weatherapp.domain.entity.WeatherThreeHours

interface WeatherRepository {

    suspend fun getWeatherFiveDays(
        city: String,
        onSuccess: (List<WeatherThreeHours>) -> Unit,
        onError: () -> Unit,
        onServerError: () -> Unit
    )
}