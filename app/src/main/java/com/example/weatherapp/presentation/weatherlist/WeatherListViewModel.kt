package com.example.weatherapp.presentation.weatherlist

import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.GetWeatherFiveDaysByCityUseCase
import com.example.weatherapp.domain.WeatherInfo
import com.example.weatherapp.presentation.base.Action
import com.example.weatherapp.presentation.base.BaseViewModel
import com.example.weatherapp.presentation.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val getWeatherFiveDaysByCityUseCase: GetWeatherFiveDaysByCityUseCase
) : BaseViewModel() {

    private val _weatherList: MutableStateFlow<List<WeatherInfo>> = MutableStateFlow(emptyList())
    private val _showErrorInternetConnection: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            getWeatherFiveDaysByCityUseCase.execute(
                onSuccess = { listWeather ->
                    _weatherList.value = listWeather
                },
                onError = {
                    _showErrorInternetConnection.value = true
                },
                onServerError = {
                    _showErrorInternetConnection.value = true
                }
            )
        }
    }

    override val state: StateFlow<State> = combine(
        _weatherList,
        _showErrorInternetConnection
    ) { weatherList, showErrorInternetConnection ->
        WeatherScreenState.Data(
            weatherList,
            showErrorInternetConnection
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        WeatherScreenState.Initial
    )

    override fun doAction(action: Action) {
        when (action) {
            is WeatherScreenAction.ShowDetailedInformation -> showDetailedWeatherInfo()
        }
    }

    private fun showDetailedWeatherInfo() {

    }
}

sealed interface WeatherScreenState : State {

    object Initial : WeatherScreenState

    data class Data(
        val list: List<WeatherInfo>,
        val showErrorInternetConnection: Boolean
    ) : WeatherScreenState
}

sealed interface WeatherScreenAction : Action {

    object ShowDetailedInformation : Action
}