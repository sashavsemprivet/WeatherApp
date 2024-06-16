package com.example.weatherapp.domain

import com.example.weatherapp.domain.entity.WeatherThreeHours
import javax.inject.Inject

class GetWeatherFiveDaysByCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend fun execute(
        city: String = "Oryol",
        onSuccess: (List<WeatherThreeHours>) -> Unit,
        onError: () -> Unit,
        onServerError: () -> Unit
    ) {
        weatherRepository.getWeatherFiveDays(
            city = city,
            onSuccess = onSuccess,
            onError = onError,
            onServerError = onServerError
        )
    }
}