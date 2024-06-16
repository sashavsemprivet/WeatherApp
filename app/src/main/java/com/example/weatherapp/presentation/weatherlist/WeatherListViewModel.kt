package com.example.weatherapp.presentation.weatherlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.models.groupWeatherByDay
import com.example.weatherapp.domain.GetWeatherFiveDaysByCityUseCase
import com.example.weatherapp.domain.entity.WeatherPerDay
import com.example.weatherapp.presentation.base.Action
import com.example.weatherapp.presentation.base.BaseViewModel
import com.example.weatherapp.presentation.base.State
import com.example.weatherapp.presentation.coreui.DateTimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val getWeatherFiveDaysByCityUseCase: GetWeatherFiveDaysByCityUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<WeatherScreenState> =
        MutableStateFlow(WeatherScreenState.Initial)
    override val state: StateFlow<State>
        get() = _state.asStateFlow()


    init {
        viewModelScope.launch {
            getWeatherFiveDaysByCityUseCase.execute(
                onSuccess = { listWeather ->

                    _state.value = WeatherScreenState.DataWeatherPerDay(
                        groupWeatherByDay(listWeather)
                    )
                },
                onError = {
                    _state.update {
                        WeatherScreenState.Error
                    }
                },
                onServerError = {
                    _state.update {
                        WeatherScreenState.Error
                    }
                }
            )
        }
    }

    override fun doAction(action: Action) {
        when (action) {
            is WeatherScreenAction.ShowDetailedInformation ->
                showDetailedWeatherInfo(action.weatherPerDay)
        }
    }

    private fun showDetailedWeatherInfo(weatherPerDay: WeatherPerDay) {
        val mappedWeatherList = weatherPerDay.listWeatherThreeHours.map { weatherThreeHours ->
            weatherThreeHours.copy(
                date = DateTimeFormatter.mapToDateOnlyTime(weatherThreeHours.date)
            )
        }
        val updatedWeatherPerDay = weatherPerDay.copy(
            listWeatherThreeHours = mappedWeatherList
        )

        _state.update {
            WeatherScreenState.DataSpecificWeather(updatedWeatherPerDay)
        }
    }
}

sealed interface WeatherScreenState : State {

    object Initial : WeatherScreenState

    data class DataWeatherPerDay(
        val listWeatherPerDay: List<WeatherPerDay>,
    ) : WeatherScreenState

    data class DataSpecificWeather(
        val weatherPerDay: WeatherPerDay
    ) : WeatherScreenState

    object Error: WeatherScreenState
}

sealed interface WeatherScreenAction : Action {

    data class ShowDetailedInformation(
        val weatherPerDay: WeatherPerDay
    ) : WeatherScreenAction
}