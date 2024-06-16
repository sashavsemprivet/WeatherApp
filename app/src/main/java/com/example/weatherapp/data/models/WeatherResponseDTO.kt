package com.example.weatherapp.data.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.domain.entity.WeatherPerDay
import com.example.weatherapp.domain.entity.WeatherThreeHours
import com.example.weatherapp.presentation.coreui.mapTempToCelsius
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.ceil

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

fun AllWeatherResponseDTO.map(): List<WeatherThreeHours> {
    return listWeather.map {
        WeatherThreeHours(
            date = it.date,
            temp = it.main.temp.mapTempToCelsius()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun groupWeatherByDay(list: List<WeatherThreeHours>): List<WeatherPerDay> {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
    return list.groupBy {
        LocalDateTime.parse(it.date, inputFormatter).format(outputFormatter)
    }.map {
        val averageTemp = it.value.map { weather -> weather.temp }.average()
        WeatherPerDay(it.value, it.key, ceil(averageTemp))
    }
}
