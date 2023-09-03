package com.eliq.weatherapp.presentation

import com.eliq.weatherapp.models.WeatherForecastResponse

data class HomeScreenUIModel(
    val locationName: String,
    val temperature: Double?,
    val temperatureUnit: String?,
    val rainfall: String?,
    val windSpeed: String?,
    val humidity: String?,
) {
    companion object {
        fun fromAPIResponse(weatherForecastResponse: WeatherForecastResponse): HomeScreenUIModel {
            val hourly = weatherForecastResponse.hourly
            val units = weatherForecastResponse.hourly_units
            return HomeScreenUIModel(
                locationName = "",
                temperature = hourly?.temperature_2m?.get(0),
                temperatureUnit = units?.temperature_2m,
                rainfall = "${hourly?.rain?.get(0) ?: "-"} ${units?.rain ?: "-"}",
                windSpeed = "${hourly?.windspeed_10m?.get(0) ?: "-"} ${units?.windspeed_10m ?: "-"}",
                humidity = "${hourly?.relativehumidity_2m?.get(0) ?: "-"} ${units?.relativehumidity_2m ?: "-"}",
            )
        }
    }
}
