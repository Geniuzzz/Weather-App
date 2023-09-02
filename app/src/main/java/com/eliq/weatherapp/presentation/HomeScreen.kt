package com.eliq.weatherapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eliq.weatherapp.R
import com.eliq.weatherapp.models.WeatherComponent
import java.text.SimpleDateFormat
import java.util.*

@Preview
@Composable
fun HomeScreen() {
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
        CurrentLocationCard(locationName = "Stockholm,\nSweden")
        CurrentDataCard(Date())
        CurrentWeatherCard()

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf(
                WeatherComponent("Rainfall", R.drawable.icon_rainfall, "3cm"),
                WeatherComponent("Wind", R.drawable.icon_wind, "19km/h"),
                WeatherComponent("Humidity", R.drawable.icon_humidity, "64%"),
            )){
                WeatherComponentCard(it)
            }
        }
    }
}

@Composable
fun CurrentWeatherCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
    ) {
        Row {
            TemperatureText(19)
            UnitText("°c")
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
fun TemperatureText(i: Int) {
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
            .padding(start = 12.dp, top = 8.dp, end = 12 .dp, bottom = 8.dp),
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