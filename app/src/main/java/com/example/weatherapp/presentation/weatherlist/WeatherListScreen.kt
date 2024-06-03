package com.example.weatherapp.presentation.weatherlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.ui.theme.Pink80
import com.example.weatherapp.ui.theme.PurpleGrey40
import com.example.weatherapp.ui.theme.PurpleGrey80

@Composable
fun WeatherListScreen() {

    val viewModel: WeatherListViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()

    if (state is WeatherScreenState.Initial) {
        Loader()
    }

    if (state is WeatherScreenState.Data) {
        WeatherListScreen(
            state = state as WeatherScreenState.Data
        )
    }
}

@Composable
private fun WeatherListScreen(state: WeatherScreenState.Data) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Text(
                fontSize = 22.sp,
                text = "Погода в Орле"
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        items(state.list) { weather ->
            DailyWeather(weather.date, weather.temp)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}


@Composable
private fun DailyWeather(date: String, temp: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(PurpleGrey80)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = date,
            fontSize = 16.sp,
            color = PurpleGrey40
        )
        Text(
            text = temp.toString(),
            fontSize = 16.sp,
            color = PurpleGrey40
        )
    }
}

@Composable
private fun Loader() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = PurpleGrey40,
            trackColor = Pink80,
        )
    }
}