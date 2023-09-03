package com.eliq.weatherapp.presentation

import com.eliq.weatherapp.models.WeatherForecastResponse
import java.text.SimpleDateFormat
import java.util.*

data class HomeScreenUIModel(
    val locationName: String,
    val temperature: Double?,
    val temperatureUnit: String?,
    val rainfall: String?,
    val windSpeed: String?,
    val humidity: String?,
) {
    companion object {

        fun defaultValues(): HomeScreenUIModel {
            return HomeScreenUIModel("--", null, null, null, null, null)
        }

        fun fromAPIResponse(weatherForecastResponse: WeatherForecastResponse): HomeScreenUIModel {
            val hourly = weatherForecastResponse.hourly
            val units = weatherForecastResponse.hourly_units
            val pattern = "yyyy-MM-dd'T'HH:mm"
            val currentDate = Date()
            currentDate.minutes = 0
            val currentDateTime = SimpleDateFormat(pattern, Locale.getDefault()).format(currentDate)
            val index = hourly?.time?.indexOf(currentDateTime) ?: 0
            return HomeScreenUIModel(
                locationName = "",
                temperature = hourly?.temperature_2m?.get(index),
                temperatureUnit = units?.temperature_2m,
                rainfall = "${hourly?.rain?.get(index) ?: "-"} ${units?.rain ?: "-"}",
                windSpeed = "${hourly?.windspeed_10m?.get(index) ?: "-"} ${units?.windspeed_10m ?: "-"}",
                humidity = "${hourly?.relativehumidity_2m?.get(index) ?: "-"} ${units?.relativehumidity_2m ?: "-"}",
            )
        }
    }
}