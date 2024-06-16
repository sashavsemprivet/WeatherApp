package com.example.weatherapp.data

import com.example.weatherapp.data.models.AllWeatherResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/forecast")
    suspend fun getWeatherFiveDays(
        @Query("q") city: String,
        @Query("appid") apiKey: String = RetrofitData.X_API_KEY
    ): Response<AllWeatherResponseDTO>
}

