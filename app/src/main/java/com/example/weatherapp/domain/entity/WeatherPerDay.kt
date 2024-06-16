package com.example.weatherapp.domain.entity

data class WeatherPerDay(
    val listWeatherThreeHours: List<WeatherThreeHours>,
    val date: String,
    val averageTemp: Double
)