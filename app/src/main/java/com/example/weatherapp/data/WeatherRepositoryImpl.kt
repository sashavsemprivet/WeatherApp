package com.example.weatherapp.data

import com.example.weatherapp.data.models.map
import com.example.weatherapp.domain.entity.WeatherThreeHours
import com.example.weatherapp.domain.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): WeatherRepository {

    override suspend fun getWeatherFiveDays(
        city: String,
        onSuccess: (List<WeatherThreeHours>) -> Unit,
        onError: () -> Unit,
        onServerError: () -> Unit
    ) {
        withContext(dispatcher){
            try {
                val result = weatherApi.getWeatherFiveDays(city = city)
                when {
                    result.isSuccessful -> {
                        val resultBody = result.body()
                        if (resultBody != null) {
                            onSuccess(resultBody.map())
                        } else {
                            onServerError()
                        }
                    }

                    else -> {
                        onServerError()
                    }
                }
            } catch (e: Throwable) {
                onError()
            }
        }
    }
}