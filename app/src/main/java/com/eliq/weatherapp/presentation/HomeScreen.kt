package com.eliq.weatherapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eliq.weatherapp.R
import com.eliq.weatherapp.models.GeocodeResult
import com.eliq.weatherapp.models.LocationDetails
import com.eliq.weatherapp.models.WeatherComponent
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(locationDetails: LocationDetails, homeViewModel: HomeViewModel = hiltViewModel()) {

    val viewState = homeViewModel.uiInfoState.value
    val query = remember { mutableStateOf("") }
    val predictions = homeViewModel.geocodingSuggestions

    LaunchedEffect(Unit) {
        homeViewModel.fetchDataForCurrentLocation(locationDetails)
    }

    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFFDE1C3),
                        Color(0xFFFDBD93),
                    )
                )
            )
            .padding(16.dp)
    ) {
        QueryLocationTextField(
            query = query.value,
            predictions = predictions.value,
            onItemClick = {
                query.value = it.getDisplayName()
                homeViewModel.onSuggestionSelected(it)
            },
            onQueryChanged = {
                query.value = it
            },
            onDoneActionClick = {
                homeViewModel.fetchGeocodingResultsFor(query.value)
            }
        )
        CurrentLocationCard(locationName = viewState.locationName)
        CurrentDataCard(Date())
        CurrentTemperatureCard(viewState.temperature ?: 0.0, viewState.temperatureUnit ?: "-")

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                listOf(
                    WeatherComponent(
                        "Rainfall",
                        R.drawable.icon_rainfall,
                        viewState.rainfall ?: "-"
                    ),
                    WeatherComponent("Wind", R.drawable.icon_wind, viewState.windSpeed ?: "-"),
                    WeatherComponent(
                        "Humidity",
                        R.drawable.icon_humidity,
                        viewState.humidity ?: "-"
                    ),
                )
            ) {
                WeatherComponentCard(it)
            }
        }
    }
}

@Composable
fun CurrentTemperatureCard(temp: Double, unit: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
    ) {
        Row {
            TemperatureText(temp)
            UnitText(unit)
        }
    }
}

@Composable
fun UnitText(s: String) {
    Text(
        text = s,
        style = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight(300),
            color = Color(0xFF303345),
        )
    )
}

@Composable
fun TemperatureText(i: Double) {
    Text(
        text = "$i",
        style = TextStyle(
            fontSize = 52.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight = FontWeight(700),
            color = Color(0xFF303345),
        )
    )
}

@Composable
fun CurrentDataCard(date: Date) {
    val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
    Text(
        text = dateFormat.format(date),
        style = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight(400),
            color = Color(0xFF9A938C),
        )
    )
}

@Composable
fun CurrentLocationCard(locationName: String) {
    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = locationName,
        style = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF313341),
        )
    )
}

@Composable
fun WeatherComponentCard(weatherComponent: WeatherComponent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 0.5.dp,
                color = Color(0x80FFFFFF),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .padding(0.5.dp)
            .background(color = Color(0x5CFFFFFF), shape = RoundedCornerShape(size = 10.dp))
            .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = weatherComponent.iconResource),
            contentDescription = "component"
        )

        Text(
            modifier = Modifier.weight(2f),
            text = weatherComponent.componentName,
            style = TextStyle(
                fontSize = 8.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF303345),
            )
        )

        Text(
            text = weatherComponent.componentValue,
            style = TextStyle(
                fontSize = 8.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF303345),
            )
        )
    }
}

@Composable
fun QueryLocationTextField(
    query: String,
    predictions: List<GeocodeResult>,
    onItemClick: (GeocodeResult) -> Unit,
    onQueryChanged: (String) -> Unit,
    onDoneActionClick: () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val view = LocalView.current

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.heightIn(max = TextFieldDefaults.MinHeight * 11)
    ) {

        item {
            QuerySearch(
                query = query,
                label = "Enter Location",
                onQueryChanged = {
                    onQueryChanged(it)
                },
                onDoneActionClick = {
                    view.clearFocus()
                    onDoneActionClick()
                })
        }

        if (predictions.isNotEmpty()) {
            items(predictions) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            onItemClick(it)
                        },
                    text = it.getDisplayName()
                )
            }
        }

    }
}

@Composable
fun QuerySearch(
    modifier: Modifier = Modifier,
    query: String,
    label: String,
    onDoneActionClick: () -> Unit = {},
    onQueryChanged: (String) -> Unit
) {
    val showClearButton = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                showClearButton.value = (focusState.isFocused)
            },
        value = query,
        onValueChange = {
            onQueryChanged(it)
        },
        label = {
            Text(text = label)
        },
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneActionClick()
            }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        )
    )
}