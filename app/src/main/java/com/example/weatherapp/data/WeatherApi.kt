package com.example.weatherapp.data

import com.example.weatherapp.data.models.AllWeatherResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/forecast")
    suspend fun getWeatherFiveDays(
        @Query("q") city: String,
        @Query("appid") apiKey: String = "bfab1c44835b504a040ac8244a013e72"
    ): Response<AllWeatherResponseDTO>
}

