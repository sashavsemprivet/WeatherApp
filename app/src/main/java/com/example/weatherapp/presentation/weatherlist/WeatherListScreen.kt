package com.example.weatherapp.presentation.weatherlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.R
import com.example.weatherapp.domain.entity.WeatherPerDay
import com.example.weatherapp.domain.entity.WeatherThreeHours
import com.example.weatherapp.presentation.coreui.ignoreHorizontalParentPadding
import com.example.weatherapp.presentation.coreui.ignoreVerticalParentPadding
import com.example.weatherapp.ui.theme.CustomBlue
import com.example.weatherapp.ui.theme.Pink80
import com.example.weatherapp.ui.theme.Purple80
import com.example.weatherapp.ui.theme.PurpleGrey80


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherListScreen() {

    val viewModel: WeatherListViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    if (state is WeatherScreenState.DataWeatherPerDay) {
        WeatherListScreen(
            state = state as WeatherScreenState.DataWeatherPerDay,
            doAction = viewModel::doAction
        )
    }

    if (state is WeatherScreenState.DataSpecificWeather) {
        ShowDetailedTemp(
            state = state as WeatherScreenState.DataSpecificWeather
        )
    }
}

@Composable
private fun WeatherListScreen(
    state: WeatherScreenState.DataWeatherPerDay,
    doAction: (WeatherScreenAction) -> Unit
) {

    LazyColumn(
        modifier = Modifier.background(Color.White),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        item {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .ignoreHorizontalParentPadding(16.dp)
                    .ignoreVerticalParentPadding(16.dp)
                    .background(CustomBlue),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.icon_weather),
                    contentDescription = null
                )
            }
        }

        item {
            Card(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Pink80),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Погода в Орле",
                        fontSize = 20.sp
                    )
                    Text(text = "Здесь будет дополнительная информация")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        item {
            Text(
                text = "Информация о погоде",
                fontSize = 18.sp
            )
        }

        items(state.listWeatherPerDay) { weather ->
            WeatherItemDay(
                weatherPerDay = weather,
                doAction = doAction
            )
        }
    }
}


@Composable
private fun WeatherItemDay(
    weatherPerDay: WeatherPerDay,
    doAction: (WeatherScreenAction) -> Unit
) {
    Box(
        modifier = Modifier
            .height(84.dp)
            .fillMaxWidth()
            .background(Purple80, RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
            .clickable {
                doAction.invoke(WeatherScreenAction.ShowDetailedInformation(weatherPerDay))
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = weatherPerDay.date,
                fontSize = 18.sp
            )
            Text(
                text = "+ ${weatherPerDay.averageTemp}",
                fontSize = 22.sp
            )
        }
    }
}

@Composable
private fun WeatherItemThreeHours(weatherThreeHours: WeatherThreeHours) {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .height(64.dp)
            .fillMaxWidth()
            .background(PurpleGrey80, RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = weatherThreeHours.date,
                fontSize = 18.sp
            )
            Text(
                text = "+ ${weatherThreeHours.temp}",
                fontSize = 22.sp
            )
        }
    }
}

@Composable
private fun ShowDetailedTemp(state: WeatherScreenState.DataSpecificWeather) {

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            WeatherItemDay(
                weatherPerDay = state.weatherPerDay,
                doAction = {}
            )
        }
        items(state.weatherPerDay.listWeatherThreeHours) { weatherThreeHours ->
            WeatherItemThreeHours(weatherThreeHours = weatherThreeHours)
        }
    }
}

//@Preview(name = "PIXEL_4_XL", device = Devices.PIXEL_4_XL)
//@Composable
//private fun TestWeatherScreenPreview() {
//
//    val state = WeatherScreenState.Data(
//        weatherThreeHoursList = listOf(
//            WeatherThreeHours("29.09.2022", 22.0),
//            WeatherThreeHours("23.09.2022", 19.0),
//            WeatherThreeHours("22.09.2022", 24.0),
//            WeatherThreeHours("21.09.2022", 22.0),
//            WeatherThreeHours("22.09.2022", 34.0),
//            WeatherThreeHours("29.09.2022", 23.0),
//            WeatherThreeHours("49.09.2022", 22.0),
//            WeatherThreeHours("59.09.2022", 30.0),
//            WeatherThreeHours("29.09.2022", 20.0),
//            WeatherThreeHours("29.09.2022", 26.0),
//            WeatherThreeHours("29.09.2022", 22.0),
//
//            ),
//        showErrorInternetConnection = false
//    )
//
//    WeatherListScreen(state)
//}