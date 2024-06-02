package com.example.weatherapp.data.models

import com.example.weatherapp.domain.WeatherInfo
import com.google.gson.annotations.SerializedName

data class AllWeatherResponseDTO(
    @SerializedName("cod")
    val code: String,
    @SerializedName("list")
    val listWeather: List<WeatherResponseDTO>
)

data class WeatherResponseDTO(
    @SerializedName("main")
    val main: TempWeatherResponseDTO,
    @SerializedName("dt_txt")
    val date: String
)

data class TempWeatherResponseDTO(
    @SerializedName("temp")
    val temp: Double
)

fun AllWeatherResponseDTO.map(): List<WeatherInfo> {
    return listWeather.map {
        WeatherInfo(
            date = it.date,
            temp = it.main.temp
        )
    }
}
